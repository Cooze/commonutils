package org.cooze.common.utils;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import ma.glasnost.orika.metadata.TypeFactory;

import java.util.List;

/**
 * @author xianzhe_song
 * @version 1.0.0
 * @desc
 * @date 2017/4/19
 */
public class BeanUtil {

    private static MapperFacade mapperFacade;
    private static MapperFactory mapperFactory;
    static {
        mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFacade = mapperFactory.getMapperFacade();
    }
    private BeanUtil(){
    }


    public static <S, D> D convert(S source,Class<D> descClass){

       return mapperFacade.map(source,descClass);
    }


    public static void convert(Object source,Object desc){
        mapperFacade.map(source,desc);
    }

    public static <S, D> D  convert(S source, Class<S> srcClass, Class<D> descClass){
        return mapperFacade.map(source,getType(srcClass),getType(descClass));
    }

    public static <S, D> List<D> convert(Iterable<S> sourceList, Class<S> sourceClass, Class<D> destinationClass) {
        return mapperFacade.mapAsList(sourceList, TypeFactory.valueOf(sourceClass), TypeFactory.valueOf(destinationClass));
    }

    public static <S, D> List<D> convert(Iterable<S> sourceList, Type<S> sourceType, Type<D> destinationType) {
        return mapperFacade.mapAsList(sourceList, sourceType, destinationType);
    }

    public static <S, D> D[] convert(D[] destination, S[] source, Class<D> destinationClass) {
        return mapperFacade.mapAsArray(destination, source, destinationClass);
    }

    public static <S, D> D[] convert(D[] destination, S[] source, Type<S> sourceType, Type<D> destinationType) {
        return mapperFacade.mapAsArray(destination, source, sourceType, destinationType);
    }

    public static <E> Type<E> getType(Class<E> clazz) {
        return TypeFactory.valueOf(clazz);
    }


}
