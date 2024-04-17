package br.sp.gov.etesp.tasks.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.sp.gov.etesp.tasks.model.Tarefa;
import br.sp.gov.etesp.tasks.repository.TarefaRepository;
import br.sp.gov.etesp.tasks.utils.StatusTarefa;

@Controller
public class HomeController {
	
	//List<Tarefa> tarefas = new ArrayList<Tarefa>();

	
	@Autowired
	TarefaRepository repository; //esta é uma variavel do tipo repository 
	@GetMapping("/")//porta 8080 , esta porta só entra requisições http (web)
	public String abrirHome(Model model) {
		List<Tarefa> tarefas = repository.findAll();
		model.addAttribute ("tarefas", tarefas);
		
		return "home";
	}
	
	@GetMapping("/encerrar/{id}")
	public String encerrarTarefa(Model model, @PathVariable Long id ) {
		
		Tarefa tarefa = repository.findById(id).get();
		tarefa.setStatus(StatusTarefa.FECHADO.name());
		tarefa.setDataFim(LocalDate.now());
		repository.save(tarefa);
		List<Tarefa> tarefas = repository.findAll();
		model.addAttribute("tarefas",tarefas);
		return "home";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluirTarefa(Model model, @PathVariable Long id) {
	
		repository.deleteById(id);
		List<Tarefa> tarefas = repository.findAll();
		model.addAttribute("tarefas",tarefas);
		return "home";
	
	}
	
	@GetMapping("/editar/{id}")
	public String editarTarefa(Model model, @PathVariable Long id) {
	
		Tarefa tarefa = repository.findById(id).get();
		model.addAttribute("tarefa", tarefa);
		return "editar-tarefa";
	
	}
	
	
	@PostMapping ("/adicionar")
public ModelAndView adicionarTarefa(Tarefa tarefa){
		
		tarefa.setStatus(StatusTarefa.ABERTO.name());		
		tarefa.setDataInicio(LocalDate.now());
		repository.save(tarefa);
		List<Tarefa> tarefas = repository.findAll();
		ModelAndView view = new ModelAndView("home");
		view.addObject("tarefas", tarefas);
		
		return view; // criar enum sempre que tiver um dado fixo que não vai mudar no projeto
	}

}
