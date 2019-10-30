package com.example.lider_express;

public class Position_BND {
    // Поля класса
    public String position;

    public String ispol;

    public String shurf;

    public String actshurf;

    // Конструктор
    public Position_BND(String id, String good, String price, String category_name) {
        this.position = id;
        this.ispol = good;
        this.shurf = price;
        this.actshurf = category_name;
    }

    // Выводим информацию по продукту
    @Override
    public String toString() {
        return String.format("ID: %s | Товар: %s | Цена: %s | Категория: %s",
                this.position, this.ispol, this.shurf, this.actshurf);
    }
}

