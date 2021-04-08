package com.github.hardlolmaster.controller;

public class CommonResponseObject {

    public static ResponseObject<String> SOMETHING_WENT_WRONG
            = new ResponseObject<>(false, "Что-то пошло не так!");
    public static ResponseObject<String> INCORRECT_INPUT
            = new ResponseObject<>(false, "Некорректные данные");

}
