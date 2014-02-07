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

import eu.agilejava.design4cd.entities.Recipe;
import eu.agilejava.design4cd.service.RecipeService;
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
 * Java EE implementation of recipes REST API.
 * 
 * @author Ivar Grimstad <ivar.grimstad@gmail.com>
 */
@Path("recipes")
public class RecipeResource {
   
   @EJB
   private RecipeService recipeService;
   @Context
   private UriInfo uriInfo;
   
   @GET
   @Path("{id}")
   @Produces(APPLICATION_JSON)
   public Response find(@PathParam("id") Long id) {
      Recipe recipe = recipeService.find(id);
      
      if(recipe == null) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }
      
      return Response.ok(recipe).build();
   }

   @GET
   @Produces(APPLICATION_JSON)
   public List<Recipe> findAll() {
      return recipeService.findAll();
   }

   @POST
   @Consumes(APPLICATION_JSON)
   public Response create(Recipe recipe) {
      recipeService.create(recipe);
      return Response.created(uriInfo.getAbsolutePathBuilder().segment(String.valueOf(recipe.getId())).build()).build();
   }

   @PUT
   @Path("{id}")
   @Consumes(APPLICATION_JSON)
   public Response edit(@PathParam("id") Long id, Recipe recipe) {
      recipe.setId(id);
      recipeService.edit(recipe);
      return Response.ok().build();
   }

   @DELETE
   @Path("{id}")
   public Response remove(@PathParam("id") Long id) {
      recipeService.remove(recipeService.find(id));
      return Response.ok().build();
   }
}
