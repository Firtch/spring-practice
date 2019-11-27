package dev.firtch.myloginapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.firtch.myloginapp.exception.InvalidActivationTokenException;
import dev.firtch.myloginapp.model.ActivationToken;
import dev.firtch.myloginapp.model.Profile;
import dev.firtch.myloginapp.service.ActivationTokenService;
import dev.firtch.myloginapp.service.ProfileService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
public class MainController {

    private final ProfileService profileService;
    private final ActivationTokenService activationTokenService;
    private final PasswordEncoder passwordEncoder;

    public MainController(ProfileService profileService, ActivationTokenService activationTokenService, PasswordEncoder passwordEncoder) {
        this.profileService = profileService;
        this.activationTokenService = activationTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping({"/login"})
    public String indexPage() {
        return "login";
    }

    @GetMapping("/signup")
    public ModelAndView signup() {
        return new ModelAndView("registrationPage", "profile", new Profile());
    }

    @GetMapping({"/", "/user/info"})
    public ModelAndView accauntInfo(Authentication authentication) {
        return new ModelAndView("accInfo", "profile",
                profileService.findByEmail(authentication.getName())
                        .orElseThrow(() -> new UsernameNotFoundException("User not found.")));
    }

    @GetMapping("/users")
    public ModelAndView usersList() {
        List<Profile> profiles = profileService.findAll();
        return new ModelAndView("users", "profiles", profiles);
    }

    @PostMapping("/user/register")
    public ModelAndView register(@Valid final Profile profile, final BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) throws JsonProcessingException {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("registrationPage", "profile", profile);
        }

        profile.setEnabled(false);
        profile.setPassword(passwordEncoder.encode(profile.getPassword()));

        ObjectMapper mapper = new ObjectMapper();
        ActivationToken activationToken = new ActivationToken(UUID.randomUUID().toString(), mapper.writeValueAsString(profile));

        String confirmUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/user/activate?token=" + activationToken.getToken();
        sendConfirmationEmail(confirmUrl);

        activationTokenService.save(activationToken);


        redirectAttributes.addFlashAttribute("registered", true);
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/user/activate")
    public ModelAndView activateUserByToken(@RequestParam(name = "token") String token, RedirectAttributes redirectAttributes) throws JsonProcessingException {

        ActivationToken activationToken = activationTokenService.findByToken(token).orElseThrow(() -> new InvalidActivationTokenException("Invalid token. Account already registred or token date was expired"));

        if (activationToken.getProfile() == null || activationToken.getProfile().equals("")) {
            throw new UnsupportedOperationException();
        }

        ObjectMapper mapper = new ObjectMapper();
        Profile profile = mapper.readValue(activationToken.getProfile(), Profile.class);
        profile.setEnabled(true);
        activationTokenService.delete(activationToken);
        profileService.save(profile);


        redirectAttributes.addFlashAttribute("activated", true);
        return new ModelAndView("redirect:/login");
    }

    private void sendConfirmationEmail(String url) {
        System.out.println("Confirm registration link: " + url);
    }

}
