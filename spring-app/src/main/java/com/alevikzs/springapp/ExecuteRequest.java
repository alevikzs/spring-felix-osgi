package com.alevikzs.springapp;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public final class ExecuteRequest {

    @NotEmpty
    private String className;
    @NotEmpty
    private String method;

}
