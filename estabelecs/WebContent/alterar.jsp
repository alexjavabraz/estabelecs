<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastrar</title>


<script type="text/javascript" src="../js/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="../js/jquery/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="../js/jquery/jquery.confirmon.js"></script>
<script type="text/javascript" src="../js/ajax.js"></script>
</head>
<body>

<form name="form1" action="alterar" method="POST" enctype="multipart/form-data">
<input type="text" name="id" value="${ESTABELECIMENTO.id}" />
<table>
	<tr>
		<td colspan="2">${error}</td>
	</tr>
	<tr>
		<td>Categoria:</td>
		<td>
			<select name="categoria" id="categoria" class="categoria"  tabindex="1">
				<c:forEach items="${categorias}" var="item">
					
					<c:choose>
						<c:when test="${ESTABELECIMENTO.categoria.idCat eq item.idCat}">
							<option value="${item.idCat}" label="${item.nomeCat}" selected="selected">${item.nomeCat}</option>
						</c:when>
						<c:otherwise>
							<option value="${item.idCat}" label="${item.nomeCat}" >${item.nomeCat}</option>
						</c:otherwise>
					</c:choose>
					
                </c:forEach>
			</select>
			
		</td>
	</tr>
	
	<tr>
		<td>Grupo:</td>
		<td>
			<select name="grupo" id="grupo" class="grupo"  tabindex="2">
				<c:forEach items="${grupos}" var="item">
						<c:choose>
							<c:when test="${ESTABELECIMENTO.grupo.idGrupo eq item.idGrupo}">
								<option value="${item.idGrupo}" label="${item.nomeGrupo}" selected="selected">${item.nomeGrupo}</option>
							</c:when>
							<c:otherwise>
								<option value="${item.idGrupo}" label="${item.nomeGrupo}" >${item.nomeGrupo}</option>
							</c:otherwise>
						</c:choose>
				</c:forEach>
			</select>
		</td>
	</tr>
	
	<tr>
		<td>Sub-Grupo:</td>
		<td>
			<select name="sgrupo" id="sgrupo" class="sgrupo"  tabindex="3">
				<c:forEach items="${subGrupos}" var="item">
						<c:choose>
							<c:when test="${ESTABELECIMENTO.sgrupo.idSg eq item.idSg}">
								<option value="${item.idSg}" label="${item.nomeSubGrupo}" selected="selected">${item.nomeSubGrupo}</option>
							</c:when>
							<c:otherwise>
								<option value="${item.idSg}" label="${item.nomeSubGrupo}" >${item.nomeSubGrupo}</option>
							</c:otherwise>
						</c:choose>
				</c:forEach>
			</select>
		</td>
	</tr>

	<tr>
		<td>Estado:</td>
		<td>
			<select name="estado" id="estado" class="estado"  tabindex="4">
				<c:forEach items="${estados}" var="item">
					<c:choose>
					
						<c:when test="${item.idEstado eq 25}">
							<option value="${item.idEstado}" checked="checked" label="${item.estado}">${item.estado}</option>
						</c:when>
						<c:otherwise>
							<option value="${item.idEstado}" label="${item.estado}">${item.estado}</option>
						</c:otherwise>
					
					</c:choose>
				
                   	
                </c:forEach>
			</select>
			
		</td>
	</tr>
	<tr>
		<td>
		Cidade:
		</td>
		<td>
			<select name="cidade" id="cidade" class="cidade"  tabindex="5">
				<c:forEach items="${cidades}" var="item">
					<c:choose>
							
								<c:when test="${item.idCidade eq 4845}">
									<option value="${item.idCidade}" selected="selected">${item.cidade }</option>
								</c:when>
								<c:otherwise>
									<option value="${item.idCidade}" >${item.cidade }</option>
								</c:otherwise>
					</c:choose>
			</c:forEach>
								
			</select>
		</td>
		
	</tr>
	<tr>
		<td>Nome fantasia:</td>
		<td><input type="text" maxlength="60" tabindex="1" name="nome" id="nome" value="${ESTABELECIMENTO.nomeFantasia}"/></td>
	</tr>
	
	<tr>
		<td>Razão Social:</td>
		<td><input type="text" maxlength="60" tabindex="1" name="razao" id="razao" value="${ESTABELECIMENTO.razaoSocial}"/></td>
	</tr>
	
	<tr>
		<td>Slogam:</td>
		<td><input type="text" maxlength="100" tabindex="1" name="logo" id="logo" value="${ESTABELECIMENTO.slogam}"/></td>
	</tr>
	
	<tr>
		<td>Endereço:</td>
		<td><input type="text" maxlength="60" tabindex="1" name="rua" id="rua" value="${ESTABELECIMENTO.endRua}"/></td>
	</tr>
	
	<tr>
		<td>Número:</td>
		<td><input type="text" maxlength="60" tabindex="1" name="numero" id="numero" value="${ESTABELECIMENTO.endNumero}"/></td>
	</tr>
	
	<tr>
		<td>Complemento:</td>
		<td><input type="text" maxlength="60" tabindex="1" name="complemento" id="complemento" value="${ESTABELECIMENTO.endComplemento}"/></td>
	</tr>
	
	<tr>
		<td>Bairro:</td>
		<td><input type="text" maxlength="60" tabindex="1" name="bairro" id="bairro" value="${ESTABELECIMENTO.bairro}"/></td>
	</tr>
	
	<tr>
		<td>CEP:</td>
		<td><input type="text" maxlength="10" tabindex="1" name="cep" id="cep" value="${ESTABELECIMENTO.cep}"/></td>
	</tr>
	
	<tr>
		<td>Telefone:</td>
		<td><input type="text" maxlength="60" tabindex="1" name="telefone" id="telefone" value="${ESTABELECIMENTO.telefone}"/></td>
	</tr>
	
	<tr>
		<td>Site:</td>
		<td><input type="text" maxlength="60" tabindex="1" name="site" id="site" value="${ESTABELECIMENTO.site}"/></td>
	</tr>
	
	<tr>
		<td>Email:</td>
		<td><input type="text" maxlength="60" tabindex="1" name="email" id="email" value="${ESTABELECIMENTO.email}"/></td>
	</tr>
	
	<tr>
		<td>Facebook ID:</td>
		<td><input type="text" maxlength="60" tabindex="1" name="facebook" id="facebook" value="${ESTABELECIMENTO.facebook}"/></td>
	</tr>
	
	<tr>
		<td>Destacar:</td>
		<td>
		
			<input type="checkbox" name="destaque" value="1"  />
		
		</td>
	</tr>
	
	<tr>
		<td>Logo (150 px x 150 px):<br/>
			<img src="../imagens/seu-logo-aqui.jpg" />
		</td>
		<td>
			<input type="file" name="upfile">
		</td>
	</tr>	
	
	<tr>
		<td colspan="2"><input type="submit" value="Gravar" /></td>
	</tr>	
		
</table>

</form>

</body>
</html>