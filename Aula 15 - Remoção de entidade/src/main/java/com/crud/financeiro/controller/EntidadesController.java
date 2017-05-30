package com.crud.financeiro.controller;

import com.crud.financeiro.model.Entidade;
import com.crud.financeiro.repository.Entidades;
import com.crud.financeiro.service.EntidadesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/entidades")
public class EntidadesController {

    private static final String INDEX = "entidade/CadastrarEntidade";

    @Autowired
    private EntidadesService entidadesService;

    @Autowired
    private Entidades entidades;

    @RequestMapping(value = "/novo")
    public String novo(Entidade entidade){
        return INDEX;
    }

    @RequestMapping(value = "/novo", method = RequestMethod.POST)
    public String salvar(@Valid Entidade entidade, BindingResult result){

        if (result.hasErrors()){
            // TODO: Mostrar mensagem de erro
            return novo(entidade);
        }

        System.out.println("Entidade: " + entidade.toString());
        entidadesService.salvar(entidade);

        return "redirect:/entidades/novo";
    }

    @RequestMapping
    public ModelAndView pesquisar(Entidade entidade){
        ModelAndView mv = new ModelAndView("entidade/PesquisarEntidade");
        mv.addObject("entidades", entidades.porNome(entidade.getNome()));
        return mv;
    }

    @RequestMapping("/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Entidade entidade){
        ModelAndView mv = new ModelAndView(INDEX);
        mv.addObject(entidade);
        return mv;
    }

    @RequestMapping(value = "/{codigo}", method = RequestMethod.DELETE)
    public String excluir(@PathVariable("codigo") Long codigo){
        entidadesService.remover(codigo);
        return "redirect:/entidades";
    }

}
