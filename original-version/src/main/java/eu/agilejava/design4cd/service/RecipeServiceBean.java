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
package eu.agilejava.design4cd.service;

import eu.agilejava.design4cd.dao.jpa.RecipeJpaDao;
import eu.agilejava.design4cd.entities.Recipe;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Java EE implementation of RecipeService.
 *
 * @see RecipeService
 *
 * @author Ivar Grimstad <ivar.grimstad@gmail.com>
 */
@Stateless
public class RecipeServiceBean implements RecipeService {
   
    @Inject
    private RecipeJpaDao recipeDao;
    
   @Override
   public int count() {
      return recipeDao.count();
   }

   @Override
   public void create(Recipe recipe) {
      recipeDao.create(recipe);
   }

   @Override
   public void edit(Recipe recipe) {
      recipeDao.edit(recipe);
   }

   @Override
   public void remove(Recipe recipe) {
      recipeDao.remove(recipe);
   }

   @Override
   public List<Recipe> findAll() {
      return recipeDao.findAll();
   }

   @Override
   public Recipe find(Object id) {
      return recipeDao.find(id);
   }
}
