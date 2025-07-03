/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import mz.sisden.sisden.configuration.security.Securities;
import mz.sisden.sisden.zkoss.Navigator;
import mz.sisden.sisden.zkoss.ZkArgument;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

import static java.util.Objects.nonNull;

@Controller
@RequiredArgsConstructor
public class SisClubeController implements ErrorController {
    private final Securities securities;

    @GetMapping("/fe/estado-dos-socios")
    public String redirectToIndex() {
        return "forward:/fe/estado-dos-socios/index.html";
    }

    @GetMapping("/login")
    public String login() {

        if (nonNull(this.securities.getUser())) {
            return "redirect:/";
        }

        return "login";
    }

    @RequestMapping("/error")
    public String error() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        HttpSession session = request.getSession();
        if (nonNull(this.securities.getUser())) {
            Navigator navigator = (Navigator) session.getAttribute(ZkArgument.NAVIGATOR.name());

            if (nonNull(navigator)) {
                navigator.go(null, false);
            }

            return "redirect:/";
        }

        return "login";
    }
}
