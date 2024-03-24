package com.alibaba.excel.exception;


import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Data convert group exception
 *
 * @author Jasonyou
 * @date 2024/03/24
 */
@Getter
@Setter
@EqualsAndHashCode
public class ExcelDataConvertGroupException extends ExcelRuntimeException{
    private Integer rowIndex;
    private List<ExcelDataConvertException> group;
    public ExcelDataConvertGroupException(){
        this(null,null,null,null);
    }
    public ExcelDataConvertGroupException(Integer rowIndex){
       this(rowIndex,null,null,null);
    }
    public ExcelDataConvertGroupException(Integer rowIndex,List<ExcelDataConvertException> group){
        this(rowIndex,group,null,null);
    }
    public ExcelDataConvertGroupException(Integer rowIndex,List<ExcelDataConvertException> group,String message){
        this(rowIndex,group,message,null);
    }
    public ExcelDataConvertGroupException(Integer rowIndex, List<ExcelDataConvertException> group,String message,Throwable cause){
        super(message,cause);
        this.rowIndex=rowIndex;
        if(group==null) {
            this.group = new ArrayList<>();
        }
    }

    @Override
    public String getMessage() {
       return Optional.ofNullable(super.getMessage()).orElse("") +"all exceptions:\n"+group.stream().map(ExcelDataConvertException::getMessage).collect(Collectors.toList()).toString();
    }

    public boolean addException(ExcelDataConvertException exception){
        return group.add(exception);
    }
}
