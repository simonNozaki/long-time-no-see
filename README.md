# long-time-no-see

CLIメモアプリケーション。ネイティブバイナリではないので、Java環境が必須。

## 開発

### 前提条件

- Java: 21
- Gradle: 8.13（gradlewを使う）
- SQLite3: 3.49.1

### データベースのセットアップ

SQLite3を用いて、ローカルのデータベースをセットアップする:

```
$ mkdir .notes
# データベース notes-db を準備する
$ sqlite3 .notes/notes-db.sqlite

sqlite> CREATE TABLE notes(
  id integer primary key,
  content text not null,
  created_at TEXT,
  updated_at TEXT
)
sqlite> .schema notes
sqlite> .exit
```

## 実行

### モード（CLI引数）

| # | サブコマンド | オプション引数     | 操作説明               | 実行例                                        | 補足                                                                                               |
|---|--------|-------------|--------------------|--------------------------------------------|--------------------------------------------------------------------------------------------------|
| 1 | list   | -           | 登録されているすべてのメモを表示する | `list`                                     |
| 2 | find   | id          | 指定したIDのメモを表示する     | `find --id="1"`                            | IDに対応するメモがないときには `NotFoundRuntimeException` をスローして異常終了する                                         |
| 3 | create | content     | メモを一件登録する          | `create --content="new note"`              |                                                                                                  |
| 4 | update | id, content | 指定したIDのメモを表示する     | `update --id="1" --content="next content"` | 必要な引数が不足しているときは `InvalidArgumentException` を、対象のメモがないときには `NotFoundRuntimeException` スローして異常終了する |
| 5 | delete | id          | 指定したIDのメモを削除する     | `delete --id="1"`                          | 必要な引数が不足しているときは `InvalidArgumentException` を、対象のメモがないときには `NotFoundRuntimeException` スローして異常終了する |

### 実行可能Jarのビルド

スタンドアローンで実行できるJarをビルドする。

```bash
./gradlew build
```

`java -jar <生成したjarファイル>` で実行できる:

```bash
> java -jar build/libs/long-time-no-see-0.0.2.jar list

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::       (v3.5.0-SNAPSHOT)

2025-04-25T13:00:06.221+09:00  INFO 23384 --- [long-time-no-see] [           main] i.g.s.l.LongTimeNoSeeApplication         : Starting LongTimeNoSeeApplication v0.0.2 using Java 21.0.6 with PID 23384 (/Users/SimonNozaki/workspace/project/apps/long-time-no-see/build/libs/long-time-no-see-0.0.2.jar started by SimonNozaki in /Users/SimonNozaki/workspace/project/apps/long-time-no-see)
2025-04-25T13:00:06.222+09:00  INFO 23384 --- [long-time-no-see] [           main] i.g.s.l.LongTimeNoSeeApplication         : No active profile set, falling back to 1 default profile: "default"
2025-04-25T13:00:06.435+09:00  INFO 23384 --- [long-time-no-see] [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2025-04-25T13:00:06.458+09:00  INFO 23384 --- [long-time-no-see] [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 19 ms. Found 1 JPA repository interface.
2025-04-25T13:00:06.924+09:00  INFO 23384 --- [long-time-no-see] [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
2025-04-25T13:00:06.972+09:00  INFO 23384 --- [long-time-no-see] [           main] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 6.6.13.Final
2025-04-25T13:00:06.992+09:00  INFO 23384 --- [long-time-no-see] [           main] o.h.c.internal.RegionFactoryInitiator    : HHH000026: Second-level cache disabled
2025-04-25T13:00:07.143+09:00  INFO 23384 --- [long-time-no-see] [           main] o.s.o.j.p.SpringPersistenceUnitInfo      : No LoadTimeWeaver setup: ignoring JPA class transformer
2025-04-25T13:00:07.179+09:00  INFO 23384 --- [long-time-no-see] [           main] org.hibernate.orm.connections.pooling    : HHH10001005: Database info:
	Database JDBC URL [Connecting through datasource 'org.springframework.jdbc.datasource.DriverManagerDataSource@540a903b']
	Database driver: undefined/unknown
	Database version: 3.49.1
	Autocommit mode: undefined/unknown
	Isolation level: undefined/unknown
	Minimum pool size: undefined/unknown
	Maximum pool size: undefined/unknown
2025-04-25T13:00:07.537+09:00  INFO 23384 --- [long-time-no-see] [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
2025-04-25T13:00:07.538+09:00  INFO 23384 --- [long-time-no-see] [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2025-04-25T13:00:07.687+09:00  INFO 23384 --- [long-time-no-see] [           main] i.g.s.l.LongTimeNoSeeApplication         : Started LongTimeNoSeeApplication in 1.665 seconds (process running for 1.919)
2025-04-25T13:00:07.762+09:00  INFO 23384 --- [long-time-no-see] [           main] i.g.s.longtimenosee.domain.note.UseCase  : 2 records found:
| #|Content                |Created at                 |Updated at                 |
| 3|created at format test |2025-04-20T16:54:50.648110 |2025-04-20T16:54:50.648110 |
| 4|new architecture now   |2025-04-21T11:37:51.553913 |2025-04-21T11:37:51.553913 |
2025-04-25T13:00:07.765+09:00  INFO 23384 --- [long-time-no-see] [ionShutdownHook] j.LocalContainerEntityManagerFactoryBean : Closing JPA EntityManagerFactory for persistence unit 'default'
```
