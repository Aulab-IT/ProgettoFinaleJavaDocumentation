package it.aulab.progetto_finale_docente.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import it.aulab.progetto_finale_docente.repositories.ArticleRepository;
import it.aulab.progetto_finale_docente.repositories.CarreerRequestRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class NotificationInterceptor implements HandlerInterceptor {

    @Autowired
    CarreerRequestRepository carreerRequestRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            int carrerCount = carreerRequestRepository.findByIsCheckedFalse().size();
            int revisedCount = articleRepository.findByIsAcceptedIsNull().size();
            modelAndView.addObject("carrerRequests", carrerCount);
            modelAndView.addObject("articlesToBeRevised", revisedCount);
        }
    }
}