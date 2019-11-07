package com.example.lider_express;

public class Position_BND {
    // Поля класса
    public String position;

    public String ispol;

    public String shurf;

    public String actshurf;

    // Конструктор
    public Position_BND(String position, String ispol, String shurf, String actshurf) {
        this.position = position;
        this.ispol = ispol;
        this.shurf = shurf;
        this.actshurf = actshurf;
    }

    // Выводим информацию по продукту
    @Override
    public String toString() {
        return String.format("ID: %s | Товар: %s | Цена: %s | Категория: %s",
                this.position, this.ispol, this.shurf, this.actshurf);
    }
}

