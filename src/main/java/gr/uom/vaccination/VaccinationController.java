package gr.uom.vaccination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class VaccinationController {

    @Autowired
    private VaccinationService service;

    @GetMapping(path="/test")
    public String getTest() {
        return service.test();
    }

}
