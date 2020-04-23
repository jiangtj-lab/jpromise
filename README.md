# JPromise

Java版的Promise，纯属娱乐，请勿使用到任何正式环境（建议使用官方的Stream API）


## How to use

通过maven安装, ${latest-version} 请到 [packages](https://github.com/jiangtj-lab/jpromise/packages) 页面查看

```xml
<dependency>
  <groupId>com.jiangtj.example</groupId>
  <artifactId>jpromise</artifactId>
  <version>${latest-version}</version>
</dependency>
```

接下来就可以愉快的使用JPromise啦

```java
class PromiseTest {

    @Test
    void exampleV1() {
        String result = new Promise<>(5)
                .then(x -> x+2)
                .then(x -> x + "!")
                .block();
        assertEquals("7!", result);
    }

    @Test
    void exampleV2() {
        new PromiseV2("初始化")
                .then(s -> s + "ok")
                .then(check -> {
                    System.out.println(check);
                    assertEquals("初始化ok", check);
                    return check;
                })
                .pending((val,p) -> {
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            p.resolve(val + ",2s等待！");
                        }
                    }, 2000);
                })
                .then(check -> {
                    System.out.println(check);
                    assertEquals("初始化ok,2s等待！", check);
                    return check;
                });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

由于Java强类型的限制，在PromiseV2中尝试都使用String...
