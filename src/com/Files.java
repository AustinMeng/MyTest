package com;

import java.util.ArrayList;
import java.util.List;

public class Files {

  public static void main(String[] args) {
    Catalogue catalogue = new Catalogue("Chapter");

    Catalogue catalogue1 = new Catalogue("Chapter 1");
    Catalogue catalogueItem11 = new CatalogueItem("Chapter1.1");
    Catalogue catalogueItem12 = new CatalogueItem("Chapter1.2");
    catalogue1.addSubCatalogue(catalogueItem11);
    catalogue1.addSubCatalogue(catalogueItem12);

    Catalogue catalogue2 = new Catalogue("Chapter 2");
    Catalogue catalogueItem21 = new CatalogueItem("Chapter2.1");
    Catalogue catalogueItem22 = new CatalogueItem("Chapter2.2");
    catalogue2.addSubCatalogue(catalogueItem21);
    catalogue2.addSubCatalogue(catalogueItem22);

    catalogue.addSubCatalogue(catalogue1);
    catalogue.addSubCatalogue(catalogue2);

    catalogue.print();
  }

}


class Catalogue {

  protected String name;
  private List<Catalogue> subCatalogues = new ArrayList<>();

  Catalogue(String name) {
    this.name = name;
  }

  public void addSubCatalogue(Catalogue subCatalogue) {
    subCatalogues.add(subCatalogue);
  }

  public List<Catalogue> getSubCatalogues() {
    return subCatalogues;
  }

  public void print() {
    System.out.println("Catalogue:"+name);
    for(int i = 0; i < subCatalogues.size(); i++) {
      subCatalogues.get(i).print();
    }
  }
}

class CatalogueItem extends Catalogue {

  CatalogueItem(String name) {
    super(name);
  }

  public void addSubCatalogue(Catalogue subCatalogue) {
    throw new UnsupportedOperationException();
  }

  public List<Catalogue> getSubCatalogues() {
    throw new UnsupportedOperationException();
  }

  public void print() {
    System.out.println("CatalogueItem:"+name);
  }
}
