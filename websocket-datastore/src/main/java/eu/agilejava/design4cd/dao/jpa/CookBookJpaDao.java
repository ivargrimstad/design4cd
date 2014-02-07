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

import eu.agilejava.design4cd.dao.CookBookDao;
import eu.agilejava.design4cd.entities.CookBook;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * JPA implementation of CookBookDao.
 *
 * @see CookBookDao
 *
 * @author Ivar Grimstad <ivar.grimstad@gmail.com>
 */
@Dependent
public class CookBookJpaDao implements CookBookDao {

   private static final Logger LOG = Logger.getLogger(CookBookJpaDao.class.getName());
   
   @Inject
   private JpaDaoHelper<CookBook> helper;

   @Override
   public void create(CookBook cookBook) {
      LOG.log(Level.CONFIG, "WDS create cookbook");
      helper.create(cookBook);
   }

   @Override
   public void edit(CookBook cookBook) {
      LOG.log(Level.CONFIG, "WDS edit edit cookbook {0}", cookBook.getId());
      helper.edit(cookBook);
   }

   @Override
   public void remove(CookBook cookBook) {
      LOG.log(Level.CONFIG, "WDS remove cookbook {0}", cookBook.getId());
      helper.remove(cookBook);
   }

   @Override
   public CookBook find(Object id) {
      LOG.log(Level.CONFIG, "WDS find cookbook {0}", id);
      return helper.find(id, CookBook.class);
   }

   @Override
   public List<CookBook> findAll() {
      LOG.log(Level.CONFIG, "WDS find All cookbooks");
      return helper.findAll(CookBook.class);
   }

   @Override
   public int count() {
      LOG.log(Level.CONFIG, "WDS count cookbooks");
      return helper.count(CookBook.class);
   }
}
