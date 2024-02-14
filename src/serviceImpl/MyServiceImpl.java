package serviceImpl;

import dao.MyDao;
import service.MyService;

public class MyServiceImpl implements MyService {

    private MyDao mydao;

    public MyServiceImpl(MyDao mydao) {
        this.mydao = mydao;
    }

    @Override
    public void myServiceMethod() {
        // dao object 의 method 를 불러와야 될 때 넣기, 즉 db 에서 어떠한 값을 받아오고 그 값을 사용 해야 되는 method 일 시 필요
        mydao.myDaoMethod();

    }



}
