package com.gameserver.utils;

import com.gameserver.exceptions.gameException.TokenErrorException;

import javax.servlet.http.HttpServletRequest;

public class ServletUtils {
    public static Integer getAccIdByRequest(HttpServletRequest request){
        return getIdByRequest(request, JwtUtil.ACC_ID_NAME);
    }

    public static Integer getPlayerIdByRequest(HttpServletRequest request){
        return getIdByRequest(request, JwtUtil.PLAYER_ID_NAME);
    }

    private static Integer getIdByRequest(HttpServletRequest request, String idName){
        String token = request.getHeader("token");
        if (token==null){
            throw new TokenErrorException();
        }
        Integer id;
        try {
            id =JwtUtil.verify(token).getClaim(idName).asInt();
           // decodedJWT = JwtUtil.verify(token);
        } catch (Exception e){
            throw new TokenErrorException();
        }
        //Integer id = decodedJWT.getClaim(idName).asInt();
        if (id==null){
            throw new TokenErrorException();
        }
        return id;
    }
}
