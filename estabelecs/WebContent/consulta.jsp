<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
	<title>Consulta de Estabelecimentos</title>
	<head>
	
<script type="text/javascript" src="../js/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="../js/jquery/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="../js/jquery/jquery.confirmon.js"></script>
<script type="text/javascript" src="../js/ajax.js"></script>
	</head>
	<body>
	<form name="consultar" method="post" action="./consultar">
		<table>
		
			<tr>
				<td>Nome:</td>
				<td>
					<input type="text" name="nome" maxlength="40" size="30" />
				</td>
			</tr>
		
			<tr>
				<td>Categoria:</td>
				<td>
					<select name="categoria" id="categoria" class="categoria"  tabindex="1">
						<c:forEach items="${categorias}" var="item">
		                   <option value="${item.idCat}" label="${item.nomeCat}">${item.nomeCat}</option>
		                </c:forEach>
					</select>
				</td>
			</tr>
			
			<tr>
				<td>Grupo:</td>
				<td>
					<select name="grupo" id="grupo" class="grupo"  tabindex="2">
					</select>
				</td>
			</tr>
			
			<tr>
				<td>Sub-Grupo:</td>
				<td>
					<select name="sgrupo" id="sgrupo" class="sgrupo"  tabindex="3">
					</select>
				</td>
			</tr>	
			<tr>
				<td colspan="2">
					<input type="submit" value="Consultar" name="Consultar"/>
				</td>
			</tr>		
			
		</table>
		
		<c:choose>
			<c:when test="${fn:length(retorno) ne 0}">
				<table width="100%" border="1" cellspadding="0" cellspacing="0">
					<tr>
						<td align="center" width="20%">Nome</td>
						<td align="center" width="10%">Sub-Grupo</td>
						<td align="center" width="10%">Endereço</td>
						<td align="center" width="5%">Número</td>
						<td align="center" width="5%">Bairro</td>
						<td align="center" width="5%">Cep</td>
						<td align="center" width="20%">Slogam</td>
						<td align="center" width="5%">Telefone</td>
						<td align="center" width="10%">Site</td>
						<td align="center" width="5%">Email</td>
						<td align="center" width="5%">Facebook</td>
					</tr>
					
					<c:forEach items="${retorno}" var="item">
						<tr>
							<td  align="center"><a href="./alterar?id=${item.id}">${item.nomeFantasia}</a></td>
							<td> </td>
							<td>${item.endRua}</td>
							<td>${item.endNumero}</td>
							<td>${item.bairro}</td>
							<td>${item.cep}</td>
							<td>${item.slogam}</td>
							<td>${item.telefone}</td>
							<td>${item.site}</td>
							<td>${item.email}</td>
							<td>${item.facebook}</td>
						</tr>
					</c:forEach>
					
					
				</table>
			</c:when>
		</c:choose>
		
	</form>
	</body>

</html>