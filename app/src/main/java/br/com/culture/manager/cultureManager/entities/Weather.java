package br.com.culture.manager.cultureManager.entities;

import java.time.LocalDateTime;

import br.com.culture.manager.cultureManager.enums.WindStrength;

public class Weather {
    private String name;
    private WindStrength windStrength;

    private String weather;

    private LocalDateTime date;
}
