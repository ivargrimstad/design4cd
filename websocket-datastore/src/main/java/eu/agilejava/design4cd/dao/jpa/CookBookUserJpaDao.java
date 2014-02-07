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

import eu.agilejava.design4cd.dao.CookBookUserDao;
import eu.agilejava.design4cd.entities.CookBookUser;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * JPA implementation of CookBookUserDao.
 *
 * @see CookBookUserDao
 *
 * @author Ivar Grimstad <ivar.grimstad@gmail.com>
 */
@Dependent
public class CookBookUserJpaDao implements CookBookUserDao {

   private static final Logger LOG = Logger.getLogger(CookBookUserJpaDao.class.getName());

   @Inject
   private JpaDaoHelper<CookBookUser> helper;
   
   @Override
   public void create(CookBookUser user) {
      LOG.log(Level.CONFIG, "WDS create user");
      helper.create(user);
   }

   @Override
   public void edit(CookBookUser user) {
      LOG.log(Level.CONFIG, "WDS edit user {0}", user.getId());
      helper.edit(user);
   }

   @Override
   public void remove(CookBookUser user) {
      LOG.log(Level.CONFIG, "WDS remove user {0}", user.getId());
      helper.remove(user);
   }

   @Override
   public CookBookUser find(Object id) {
      LOG.log(Level.CONFIG, "WDS find user {0}", id);
      return helper.find(id, CookBookUser.class);
   }

   @Override
   public List<CookBookUser> findAll() {
      LOG.log(Level.CONFIG, "WDS find all users");
      return helper.findAll(CookBookUser.class);
   }

   @Override
   public int count() {
      LOG.log(Level.CONFIG, "WDS count users");
      return helper.count(CookBookUser.class);
   }
}
