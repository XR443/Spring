package com.github.hardlolmaster.controller;

public class CommonResponseObject {

    public static ResponseObject<String> SOMETHING_WENT_WRONG
            = new ResponseObject<>(false, "Что-то пошло не так!");

}
