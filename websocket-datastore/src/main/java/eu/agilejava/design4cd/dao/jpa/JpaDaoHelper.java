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

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Base class for JPA Dao.
 * 
 * @author Ivar Grimstad <ivar.grimstad@gmail.com>
 */
@Dependent
public class JpaDaoHelper<T> {

   @PersistenceContext(unitName = "cookingPU")
   private EntityManager em;
   
   public void create(T entity) {
      em.persist(entity);
   }

   public void edit(T entity) {
      em.merge(entity);
   }

   public void remove(T entity) {
      em.remove(em.merge(entity));
   }

   public T find(Object id, Class<T> entityClass) {
      return em.find(entityClass, id);
   }

   public List<T> findAll(Class<T> entityClass) {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(entityClass));
      return em.createQuery(cq).getResultList();
   }

   public int count(Class<T> entityClass) {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<T> rt = cq.from(entityClass);
      cq.select(em.getCriteriaBuilder().count(rt));
      
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
   }
}
