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
package eu.agilejava.design4cd.api;

import eu.agilejava.design4cd.entities.CookBookUser;
import eu.agilejava.design4cd.service.CookBookUserService;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Java EE implementation of users REST API.
 * 
 * @author Ivar Grimstad <ivar.grimstad@gmail.com>
 */
@Path("users")
public class CookBookUserResource  {
   
   @EJB
   private CookBookUserService cookBookUserService;
   
   @Context
   private UriInfo uriInfo;

   @GET
   @Path("{id}")
   @Produces(APPLICATION_JSON)
   public Response find(@PathParam("id") Long id) {
      CookBookUser user = cookBookUserService.find(id);
      
      if (user == null ) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }
      
      return Response.ok(user).build();
   }

   @GET
   @Produces(APPLICATION_JSON)
   public List<CookBookUser> findAll() {
      return cookBookUserService.findAll();
   }

   @POST
   @Consumes(APPLICATION_JSON)
   public Response create(CookBookUser user) {
      cookBookUserService.create(user);
      return Response.created(uriInfo.getAbsolutePathBuilder().segment(String.valueOf(user.getId())).build()).build();
   }

   @PUT
   @Path("{id}")
   @Consumes(APPLICATION_JSON)
   public Response edit(@PathParam("id") Long id, CookBookUser user) {
      user.setId(id);
      cookBookUserService.edit(user);
      return Response.ok().build();
   }

   @DELETE
   @Path("{id}")
   public Response remove(@PathParam("id") Long id) {
      cookBookUserService.remove(cookBookUserService.find(id));
      return Response.ok().build();
   }
}
