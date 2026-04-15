package org.example.serviciodevice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeletedResult {
    private List<String> deleted;
    private List<String> notDeleted;

    public DeletedResult(List<String>deleted,List<String>notDeleted){
        this.deleted=deleted;
        this.notDeleted=notDeleted;
    }
}
