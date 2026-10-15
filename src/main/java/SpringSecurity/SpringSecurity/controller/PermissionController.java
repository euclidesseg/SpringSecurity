package SpringSecurity.SpringSecurity.controller;


import SpringSecurity.SpringSecurity.dto.SavePermissionDTO;
import SpringSecurity.SpringSecurity.dto.ShowPermissionDTO;
import SpringSecurity.SpringSecurity.service.impl.PermissionServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @Autowired
    private PermissionServiceImpl permissionService;
    @GetMapping
    public ResponseEntity<Page<ShowPermissionDTO>> findAll(Pageable pageable){
        Page<ShowPermissionDTO> showPermissionPage = this.permissionService.findAll(pageable);
        if (showPermissionPage.hasContent()){
            return ResponseEntity.status(HttpStatus.OK).body(showPermissionPage);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{permissionId}")
    public ResponseEntity<ShowPermissionDTO> findOne(@PathVariable Long permissionId){
        Optional<ShowPermissionDTO> showPermission = this.permissionService.findOneById(permissionId);
        if(showPermission.isPresent()){
            return ResponseEntity.ok().body(showPermission.get());
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ShowPermissionDTO addOnePermission(@RequestBody SavePermissionDTO savePermissionDTO){
        return this.permissionService.addOnePermission(savePermissionDTO);
    }
    @DeleteMapping
    public ResponseEntity <String> deleteOnePermission(@RequestBody @Valid SavePermissionDTO savePermissionDTO){
        boolean response =  this.permissionService.removeOnePermission(savePermissionDTO);
        if(response){
            return ResponseEntity.ok("Is deleted with exit");
        }
        else{
            return ResponseEntity.badRequest().body("deletion failed"+ ResponseEntity.status(HttpStatus.NOT_FOUND));
        }
    }
}
