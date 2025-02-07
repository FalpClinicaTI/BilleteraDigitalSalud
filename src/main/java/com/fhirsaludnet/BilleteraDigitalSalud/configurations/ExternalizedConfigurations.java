package com.fhirsaludnet.BilleteraDigitalSalud.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
// clase POJO que representa las configuraciones externas

public class ExternalizedConfigurations {

    //INFO: Se debe mantener el orden de las variables
    // tal como se encuentran en el archivo de propiedades
    private String name;
    private String version;
    private String description;
    private String author;
    private String language;
    private String framework;
    private String country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFramework() {
        return framework;
    }

    public void setFramework(String framework) {
        this.framework = framework;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "ExternalizedConfigurations{" +
                "Nombre='" + name + '\'' +
                ", Versión='" + version + '\'' +
                ", Description='" + description + '\'' +
                ", Autor='" + author + '\'' +
                ", Lenguaje='" + language + '\'' +
                ", framework='" + framework + '\'' +
                ", Ciudad='" + country + '\'' +
                '}';
    }
}
