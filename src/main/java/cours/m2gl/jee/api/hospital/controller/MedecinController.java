package cours.m2gl.jee.api.hospital.controller;

import cours.m2gl.jee.api.hospital.model.Medecin;
import cours.m2gl.jee.api.hospital.model.Service;
import cours.m2gl.jee.api.hospital.model.Specialite;
import cours.m2gl.jee.api.hospital.service.IServiceService;
import cours.m2gl.jee.api.hospital.service.ISpecialiteService;
import cours.m2gl.jee.api.hospital.service.MedecinService;
import cours.m2gl.jee.api.hospital.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/medecin")
public class MedecinController {
    @Autowired
    private MedecinService medecinService;

    @Autowired
    private IServiceService serviceService;

    @Autowired
    private ISpecialiteService specialiteService;


    @PreAuthorize("hasAuthority('ROLE_SECRETAIRE') or hasAuthority('ROLE_MEDECIN')")
    @PostMapping("/add")
    public @ResponseBody Medecin add(@RequestBody Medecin medecin){
        if(medecin.getService() != null){
            Service s = serviceService.findById(medecin.getService().getId());
            medecin.setService(s);
        }
        if(medecin.getSpecialites() != null){
            List<Specialite> sps = new ArrayList<>();
            List<Specialite> parc = medecin.getSpecialites();
            for (Specialite ss : parc) {
                sps.add(specialiteService.findById(ss.getId()));
            }
            medecin.setSpecialites(sps);
        }

        return medecinService.save(medecin);
    }

    @PreAuthorize("hasAuthority('ROLE_MEDECIN')")
    @GetMapping("/all")
    public @ResponseBody List<Medecin> add(){
        return medecinService.findAll();
    }
}
