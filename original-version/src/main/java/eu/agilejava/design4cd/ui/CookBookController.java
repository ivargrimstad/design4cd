/*
 * The MIT License
 *
 * Copyright 2013 Ivar Grimstad <ivar.grimstad@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package eu.agilejava.design4cd.ui;

import eu.agilejava.design4cd.entities.CookBook;
import eu.agilejava.design4cd.service.CookBookService;
import eu.agilejava.design4cd.ui.util.JsfUtil;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

/**
 * JSF controller for cookbooks.
 * 
 * @author Ivar Grimstad <ivar.grimstad@gmail.com>
 */
@Named("cookBookController")
@ConversationScoped
public class CookBookController implements Serializable {

   @EJB
   private CookBookService cookBookService;

   private CookBook current;
   private DataModel items = null;

   public CookBookController() {
   }

   public CookBook getSelected() {
      if (current == null) {
         current = new CookBook();
      }
      return current;
   }

   public String prepareList() {
      recreateModel();
      return "List";
   }

   public String prepareView() {
      current = (CookBook) getItems().getRowData();
      return "View";
   }

   public String prepareCreate() {
      current = new CookBook();
      return "Create";
   }

   public String create() {
      try {
         cookBookService.create(current);
         JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/translations").getString("CookBookCreated"));
         return prepareCreate();
      } catch (Exception e) {
         JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/translations").getString("PersistenceErrorOccured"));
         return null;
      }
   }

   public String prepareEdit() {
      current = (CookBook) getItems().getRowData();
      return "Edit";
   }

   public String update() {
      try {
         cookBookService.edit(current);
         JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/translations").getString("CookBookUpdated"));
         return "View";
      } catch (Exception e) {
         JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/translations").getString("PersistenceErrorOccured"));
         return null;
      }
   }

   public String delete() {
      current = (CookBook) getItems().getRowData();
      performDelete();
      recreateModel();
      return "List";
   }

   private void performDelete() {
      try {
         cookBookService.remove(current);
         JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/translations").getString("CookBookDeleted"));
      } catch (Exception e) {
         JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/translations").getString("PersistenceErrorOccured"));
      }
   }

   public DataModel getItems() {
      if (items == null) {
         items = new ListDataModel(cookBookService.findAll());
      }
      return items;
   }

   private void recreateModel() {
      items = null;
   }

   public SelectItem[] getItemsAvailableSelectMany() {
      return JsfUtil.getSelectItems(cookBookService.findAll(), false);
   }

   public SelectItem[] getItemsAvailableSelectOne() {
      return JsfUtil.getSelectItems(cookBookService.findAll(), true);
   }

   public CookBook getCookBook(java.lang.Long id) {
      return cookBookService.find(id);
   }

   @FacesConverter(forClass = CookBook.class)
   public static class CookBookControllerConverter implements Converter {

      @Override
      public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
         if (value == null || value.length() == 0) {
            return null;
         }
         CookBookController controller = (CookBookController) facesContext.getApplication().getELResolver().
                 getValue(facesContext.getELContext(), null, "cookBookController");
         return controller.getCookBook(getKey(value));
      }

      java.lang.Long getKey(String value) {
         java.lang.Long key;
         key = Long.valueOf(value);
         return key;
      }

      String getStringKey(java.lang.Long value) {
         StringBuilder sb = new StringBuilder();
         sb.append(value);
         return sb.toString();
      }

      @Override
      public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
         if (object == null) {
            return null;
         }
         if (object instanceof CookBook) {
            CookBook o = (CookBook) object;
            return getStringKey(o.getId());
         } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + CookBook.class.getName());
         }
      }
   }
}
