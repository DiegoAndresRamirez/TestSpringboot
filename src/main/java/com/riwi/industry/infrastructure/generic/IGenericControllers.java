package com.riwi.industry.infrastructure.generic;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IGenericControllers <DTOReq, DTORes, ID>{

    ResponseEntity<DTORes> create(DTOReq dtoReq); // <1>

    ResponseEntity<DTORes> update(DTOReq dtoReq, ID id); // <2>

    ResponseEntity<DTORes> getById(ID id); // <3>

    ResponseEntity<List<DTORes>> getAll(); // <4>

    ResponseEntity<String> delete(ID id); // <5>
}
