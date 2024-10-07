package com.riwi.industry.application.generic;

import java.util.List;

public interface IGenericCrud <DTOReq, DTORes, ID, String >{

    DTORes create(DTOReq dtoReq);
    DTORes update(DTOReq dtoReq, ID id);
    DTORes getById(ID id);
    List<DTORes> getAll();
    String delete(ID id);
}
