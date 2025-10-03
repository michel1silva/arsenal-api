package com.michel.first_spring_app.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GlobalErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        try {
            Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
            
            if (status != null) {
                Integer statusCode = Integer.valueOf(status.toString());
                
                if (statusCode == HttpStatus.NOT_FOUND.value()) {
                    // 404 - Página não encontrada
                    model.addAttribute("errorCode", "404");
                    model.addAttribute("errorTitle", "Página Não Encontrada");
                    model.addAttribute("errorMessage", "A página que você está procurando não existe ou foi movida.");
                    model.addAttribute("suggestion", "Verifique o endereço ou use o menu de navegação.");
                    return "error-404";
                } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                    // 500 - Erro interno do servidor
                    model.addAttribute("errorCode", "500");
                    model.addAttribute("errorTitle", "Erro Interno do Servidor");
                    model.addAttribute("errorMessage", "Algo deu errado no servidor. Nossa equipe foi notificada.");
                    model.addAttribute("suggestion", "Tente novamente em alguns minutos ou entre em contato conosco.");
                    return "error-500";
                } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                    // 403 - Acesso negado
                    model.addAttribute("errorCode", "403");
                    model.addAttribute("errorTitle", "Acesso Negado");
                    model.addAttribute("errorMessage", "Você não tem permissão para acessar esta página.");
                    model.addAttribute("suggestion", "Entre em contato com o administrador se precisar de acesso.");
                    return "error-403";
                } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                    // 400 - Requisição inválida
                    model.addAttribute("errorCode", "400");
                    model.addAttribute("errorTitle", "Requisição Inválida");
                    model.addAttribute("errorMessage", "A requisição não pôde ser processada devido a dados inválidos.");
                    model.addAttribute("suggestion", "Verifique os dados enviados e tente novamente.");
                    return "error-generic";
                }
            }
        } catch (Exception e) {
            // Em caso de erro no próprio controlador de erro, usar fallback
            model.addAttribute("errorCode", "Erro");
            model.addAttribute("errorTitle", "Erro no Sistema");
            model.addAttribute("errorMessage", "Ocorreu um erro inesperado no sistema.");
            model.addAttribute("suggestion", "Tente novamente ou entre em contato com o suporte.");
            return "error-generic";
        }
        
        // Erro genérico para casos não específicos
        model.addAttribute("errorCode", "Erro");
        model.addAttribute("errorTitle", "Algo deu errado");
        model.addAttribute("errorMessage", "Ocorreu um erro inesperado. Tente novamente.");
        model.addAttribute("suggestion", "Se o problema persistir, entre em contato conosco.");
        return "error-generic";
    }
}
