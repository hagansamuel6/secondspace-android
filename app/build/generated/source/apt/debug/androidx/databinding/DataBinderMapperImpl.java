package androidx.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new com.samapps.hp.a2ndspace.DataBinderMapperImpl());
  }
}
