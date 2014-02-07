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
package eu.agilejava.design4cd.dao.jpa;

import eu.agilejava.design4cd.entities.Recipe;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * JPA implementation of RecipeDao.
 * 
 * @author Ivar Grimstad <ivar.grimstad@gmail.com>
 */
@Dependent
public class RecipeJpaDao {

   private static final Logger LOG = Logger.getLogger(RecipeJpaDao.class.getName());
   
   @Inject
   private JpaDaoHelper<Recipe> helper;
   
   public void create(Recipe recipe) {
      LOG.log(Level.CONFIG, "JPA create recipe");
      helper.create(recipe);
   }

   public void edit(Recipe recipe) {
      LOG.log(Level.CONFIG, "JPA edit recipe {0}", recipe.getId());
      helper.edit(recipe);
   }

   public void remove(Recipe recipe) {
      LOG.log(Level.CONFIG, "JPA remove recipe {0}", recipe.getId());
      helper.remove(recipe);
   }

   public Recipe find(Object id) {
      LOG.log(Level.CONFIG, "JPA find recipe {0}", id);
      return helper.find(id, Recipe.class);
   }

   public List<Recipe> findAll() {
      LOG.log(Level.CONFIG, "JPA find all recipes");
      return helper.findAll(Recipe.class);
   }

   public int count() {
      LOG.log(Level.CONFIG, "JPA count recipes");
      return helper.count(Recipe.class);
   }
}
