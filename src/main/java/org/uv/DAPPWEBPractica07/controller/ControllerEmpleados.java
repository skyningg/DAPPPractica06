package org.uv.DAPPWEBPractica07.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uv.DAPPWEBPractica07.models.Empleados;
import org.uv.DAPPWEBPractica07.repository.RepositoryEmpleados;

@RestController
@RequestMapping("/empleados")
public class ControllerEmpleados {

    private final RepositoryEmpleados repositoryEmpleados;

    @Autowired
    public ControllerEmpleados(RepositoryEmpleados repositoryEmpleados) {
        this.repositoryEmpleados = repositoryEmpleados;
    }

    @GetMapping("/holamundo/{val}")
    public String holamundo(@PathVariable String val) {
        return "Hola mundo desde Spring Boot " + val;
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public List<Empleados> listEmpleados() {
        return repositoryEmpleados.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public Empleados findByID(@PathVariable Long id) {
        Optional<Empleados> emp = repositoryEmpleados.findById(id);
        if (!emp.isEmpty())
            return emp.get();
        else
            return null;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Empleados> guardar(@RequestBody Empleados emp) {
        Empleados empNew = repositoryEmpleados.save(emp);
        return ResponseEntity.ok(empNew);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Empleados> borrar(@PathVariable Long id) {
        Optional<Empleados> emp = repositoryEmpleados.findById(id);
        if (!emp.isEmpty()) {
            repositoryEmpleados.deleteById(id);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.notFound().build(); // Checar la diferencia entre notFound y noContent
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Empleados> actualizar(@PathVariable Long id, @RequestBody Empleados emp) {
        Optional<Empleados> empOld = repositoryEmpleados.findById(id);
        if (!empOld.isEmpty()) {
            Empleados empUpdate = empOld.get();
            empUpdate.setNombre(emp.getNombre());
            empUpdate.setDireccion(emp.getDireccion());
            empUpdate.setTelefono(emp.getTelefono());
            repositoryEmpleados.save(empUpdate);
            return ResponseEntity.ok(empUpdate);
        } else
            return ResponseEntity.notFound().build();
    }

}
