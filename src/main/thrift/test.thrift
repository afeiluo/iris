namespace java com.afeiluo.thrift.inter

struct Person{
	1: string name,
	2: i32 age

}

service TestService{
	i64 ping();

	Person findPerson(1: i64 uid);
}