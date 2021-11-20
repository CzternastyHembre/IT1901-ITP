module rest {
    requires spring.web;
    requires spring.beans;
    requires spring.boot;
    requires spring.context;
    requires spring.boot.autoconfigure;
    requires core;
    requires com.google.gson;
    opens it1901.rest;

}