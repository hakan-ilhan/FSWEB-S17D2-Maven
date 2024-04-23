package com.workintech.s17d2.rest;

import com.workintech.s17d2.model.JuniorDeveloper;
import com.workintech.s17d2.tax.DeveloperTax;
import jakarta.annotation.PostConstruct;
import com.workintech.s17d2.model.Developer;
import com.workintech.s17d2.model.Experience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.workintech.s17d2.tax.Taxable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/developers")
public class DeveloperController {
    public Map<Integer, Developer> developers;
    private  Taxable developerTax;
    @PostConstruct
    public void init(){
        this.developers = new HashMap<>();
        developers.put(1,new Developer(1,"Hakan",32000.0,Experience.JUNIOR));
    }
    @Autowired
    public DeveloperController(Taxable developerTax){
        this.developerTax = developerTax;
    }
    @GetMapping("/")
    public List<Developer> allDevs(){
        return developers.values().stream().toList();
    }
    @GetMapping("/${id}")
    public Developer getDev(@PathVariable int id){
        return developers.get(id);
    }
    @PostMapping("/developers")
    public Developer addDev(@RequestBody Developer developer){
        if(developer.getExperience() == Experience.JUNIOR){
            double tax = developer.getSalary() * (developerTax.getSimpleTaxRate() / 100);
            developer.setSalary(developer.getSalary() - tax);
            developers.put(developer.getId(),developer);
        } else if(developer.getExperience() == Experience.MID){
            double tax = developer.getSalary() * (developerTax.getSimpleTaxRate() / 100);
            developer.setSalary(developer.getSalary() - tax);
            developers.put(developer.getId(),developer);
        } else if(developer.getExperience() == Experience.SENIOR){
            double tax = developer.getSalary() * (developerTax.getSimpleTaxRate() / 100);
            developer.setSalary(developer.getSalary() - tax);
            developers.put(developer.getId(),developer);
        }
        return developer;
    }
    @PutMapping("/${id}")
    public Developer updateDev(@PathVariable int id, @RequestBody Developer developer){
        developers.replace(id,developer);

        return developer;
    }
    @DeleteMapping("/${id}")
    public Developer removeDev(@PathVariable int id){
        Developer dev = developers.get(id);
        developers.remove(id,dev);
        return dev;
    }





}
