package androidx.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new com.example.cloud_firestore_project.DataBinderMapperImpl());
  }
}
