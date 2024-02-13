package com.loomi.ecommerce.controller;

import com.loomi.ecommerce.entity.Client;
import com.loomi.ecommerce.entity.Order;
import com.loomi.ecommerce.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@SecurityRequirement(name = "Bearer Authentication")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Operation(summary = "Find all Client", tags = "Client")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Client>> findAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @Operation(summary = "Find Client by full name", tags = "Client")
    @GetMapping(path = "/fullname/{client_full_name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Client>> findByName(@PathVariable(name = "client_full_name") String fullName) {
        return ResponseEntity.ok(clientService.findByFullName(fullName));
    }

    @Operation(summary = "Find Client by ID", tags = "Client")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @GetMapping(path = "/{client_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Client> findById(@PathVariable(name = "client_id") Long id) {
        Client client = clientService.findById(id);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(client, HttpStatus.OK);
        }
    }

    @Operation(summary = "Find Order by CLientId", tags = "Client")
    @GetMapping(path = "/order/{client_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Order>> findOrderByClientId(@PathVariable(name = "client_id") Long id) {
        List<Order> listClient = clientService.findListOrderByCLientId(id);
        if (listClient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(listClient, HttpStatus.OK);
        }
    }

    @Operation(summary = "Save a new Client", tags = "Client")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Client> save(@RequestBody Client client) {
        try {
            client = clientService.save(client);
            return new ResponseEntity<>(client, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Update a Client", tags = "Client")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @PutMapping(path = "/{client_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Client> update(@PathVariable(name = "client_id") Long id,
                                         @RequestBody Client client) {
        client.setId(id);
        try {
            client = clientService.update(client);
            if (client == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(client, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete a Client", tags = "Client")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "No Content")})
    @DeleteMapping(path = "/{client_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteById(@PathVariable(name = "client_id") Long id) {
        clientService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
