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

import eu.agilejava.design4cd.entities.CookBookUser;
import eu.agilejava.design4cd.service.CookBookUserService;
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
 * JSF controller for users.
 * 
 * @author Ivar Grimstad <ivar.grimstad@gmail.com>
 */
@Named("cookBookUserController")
@ConversationScoped
public class CookBookUserController implements Serializable {

   private CookBookUser current;
   private DataModel items = null;
   @EJB
   private CookBookUserService cookBookUserService;

   public CookBookUserController() {
   }

   public CookBookUser getSelected() {
      if (current == null) {
         current = new CookBookUser();
      }
      return current;
   }

   public String prepareList() {
      recreateModel();
      return "List";
   }

   public String prepareView() {
      current = (CookBookUser) getItems().getRowData();
      return "View";
   }

   public String prepareCreate() {
      current = new CookBookUser();
      return "Create";
   }

   public String create() {
      try {
         cookBookUserService.create(current);
         JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/translations").getString("CookBookUserCreated"));
         return prepareCreate();
      } catch (Exception e) {
         JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/translations").getString("PersistenceErrorOccured"));
         return null;
      }
   }

   public String prepareEdit() {
      current = (CookBookUser) getItems().getRowData();
      return "Edit";
   }

   public String update() {
      try {
         cookBookUserService.edit(current);
         JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/translations").getString("CookBookUserUpdated"));
         return "View";
      } catch (Exception e) {
         JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/translations").getString("PersistenceErrorOccured"));
         return null;
      }
   }

   public String delete() {
      current = (CookBookUser) getItems().getRowData();
      performDelete();
      recreateModel();
      return "List";
   }

   private void performDelete() {
      try {
         cookBookUserService.remove(current);
         JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/translations").getString("CookBookUserDeleted"));
      } catch (Exception e) {
         JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/translations").getString("PersistenceErrorOccured"));
      }
   }

   public DataModel getItems() {
      if (items == null) {
         items = new ListDataModel(cookBookUserService.findAll());
      }
      return items;
   }

   private void recreateModel() {
      items = null;
   }

   public SelectItem[] getItemsAvailableSelectMany() {
      return JsfUtil.getSelectItems(cookBookUserService.findAll(), false);
   }

   public SelectItem[] getItemsAvailableSelectOne() {
      return JsfUtil.getSelectItems(cookBookUserService.findAll(), true);
   }

   public CookBookUser getCookBookUser(java.lang.Long id) {
      return cookBookUserService.find(id);
   }

   @FacesConverter(forClass = CookBookUser.class)
   public static class CookBookUserControllerConverter implements Converter {

      @Override
      public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
         if (value == null || value.length() == 0) {
            return null;
         }
         CookBookUserController controller = (CookBookUserController) facesContext.getApplication().getELResolver().
                 getValue(facesContext.getELContext(), null, "cookBookUserController");
         return controller.getCookBookUser(getKey(value));
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
         if (object instanceof CookBookUser) {
            CookBookUser o = (CookBookUser) object;
            return getStringKey(o.getId());
         } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + CookBookUser.class.getName());
         }
      }
   }
}
