import 'package:flutter/material.dart';
import 'package:toggle_switch/toggle_switch.dart';

class AccountPage extends StatefulWidget {
  const AccountPage({Key? key}) : super(key: key);

  @override
  State<AccountPage> createState() => _AccountPageState();
}

class _AccountPageState extends State<AccountPage> {
  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scrollbar(
        child: SingleChildScrollView(
          child: Padding(
            padding: EdgeInsets.symmetric(vertical: 15, horizontal: 15),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              mainAxisAlignment: MainAxisAlignment.start,
              children: [
                 Text(
                  'Ваш логин: p.tolst',
                  style: TextStyle(
                      fontSize: 24,
                      fontWeight: FontWeight
                          .bold), // TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
                  maxLines: 1,
                  overflow: TextOverflow.ellipsis,
                ),
                 SizedBox(
                  height: 15,
                ),
                TextFormField(
                  maxLines: 1,
                  validator: (v) {},
                  textInputAction: TextInputAction.next,
                  keyboardType: TextInputType.text,
                  decoration: InputDecoration(
                    fillColor: Colors.white,
                    filled: true,
                    prefixIcon: const Icon(Icons.account_circle),
                    hintText: "Название канала",
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(10),
                    ),
                  ),
                  onChanged: (v) {},
                ),
                SizedBox(
                  height: 15,
                ),
                TextFormField(
                  maxLines: 5,
                  validator: (v) {},
                  textInputAction: TextInputAction.next,
                  keyboardType: TextInputType.visiblePassword,
                  decoration: InputDecoration(
                    fillColor: Colors.white,
                    filled: true,
                    prefixIcon: const Icon(Icons.info_rounded),
                    hintText: "Описание канала",
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(10),
                    ),
                  ),
                  onChanged: (v) {},
                ),
                SizedBox(
                  //width: 10,
                  height: 15,
                ),
                TextFormField(
                  validator: (v) {},
                  textInputAction: TextInputAction.next,
                  keyboardType: TextInputType.visiblePassword,
                  decoration: InputDecoration(
                    fillColor: Colors.white,
                    filled: true,
                    prefixIcon: const Icon(Icons.account_circle),
                    hintText: "Пароль",
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(10),
                    ),
                  ),
                  onChanged: (v) {},
                ),
                SizedBox(
                  height: 15,
                ),
                Row(
                    // crossAxisAlignment: CrossAxisAlignment.start,
                    // mainAxisAlignment: MainAxisAlignment.start,
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(
                        'Возможность оценивания',
                        style: TextStyle(fontSize: 16),
                        maxLines: 1,
                        overflow: TextOverflow.ellipsis,
                      ),
                      ToggleSwitch(
                        minWidth: 50.0,
                        initialLabelIndex: 1,
                        cornerRadius: 20.0,
                        activeFgColor: Colors.white,
                        inactiveBgColor: Colors.grey,
                        inactiveFgColor: Colors.white,
                        totalSwitches: 2,
                        labels: ['Да', 'Нет'],
                        // icons: [Icons.bus_alert, Icons.add_alarm],
                        activeBgColors: [
                          [Colors.blue],
                          [Colors.pink]
                        ],
                        onToggle: (index) {
                          print('switched to: $index');
                        },
                      ),
                    ]),
                SizedBox(
                  height: 15,
                ),
                Row(
                    // crossAxisAlignment: CrossAxisAlignment.start,
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(
                        'Возможность комментирования',
                        style: TextStyle(fontSize: 16),
                        maxLines: 1,
                        overflow: TextOverflow.ellipsis,
                      ),
                      ToggleSwitch(
                        minWidth: 50.0,
                        initialLabelIndex: 1,
                        cornerRadius: 20.0,
                        activeFgColor: Colors.white,
                        inactiveBgColor: Colors.grey,
                        inactiveFgColor: Colors.white,
                        totalSwitches: 2,
                        labels: ['Да', 'Нет'],
                        // icons: [Icons.bus_alert, Icons.add_alarm],
                        activeBgColors: [
                          [Colors.blue],
                          [Colors.pink]
                        ],
                        onToggle: (index) {
                          print('switched to: $index');
                        },
                      ),
                    ]),

                Padding(
                  padding:
                      const EdgeInsets.symmetric(horizontal: 15, vertical: 20),
                  child: MaterialButton(
                    height: 110,
                    shape: RoundedRectangleBorder(
                        side: BorderSide(width: 1.5),
                        borderRadius: BorderRadius.all(Radius.circular(5.0))),
                    onPressed: () {
                      print('+');
                    },
                    child: Center(
                      child: Column(
                        //alignment: Alignment.topCenter,
                        children: [
                          Text(
                            '+',
                            style: TextStyle(
                                fontWeight: FontWeight.bold, fontSize: 50),
                            maxLines: 1,
                            overflow: TextOverflow.ellipsis,
                          ),
                          Text(
                            'Добавить фото фона',
                            style: TextStyle(
                                fontWeight: FontWeight.bold, fontSize: 24),
                            maxLines: 1,
                            overflow: TextOverflow.ellipsis,
                          ),
                        ],
                      ),
                    ),
                  ),
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    MaterialButton(
                      height: 35,
                      color: const Color.fromRGBO(65, 75, 178, 1),
                      child: const Text(
                        "Удалить канал",
                        style: TextStyle(color: Colors.white),
                      ),
                      onPressed: () {
                        print("Удалить канал");
                      },
                    ),
                    MaterialButton(
                      onPressed: () {
                        print("Выйти из аккаунта");
                      },
                      color: const Color.fromRGBO(65, 75, 178, 1),
                      child: Text(
                        "Выйти из аккаунта",
                        style: Theme.of(context).textTheme.bodyText1,
                      ),
                    )
                  ],
                ),
                Center(
                  child: MaterialButton(
                        height: 35,
                        color: const Color.fromRGBO(65, 75, 178, 1),
                        child: const Text(
                          "Сохранить изменения",
                          style: TextStyle(color: Colors.white),
                        ),
                        onPressed: () {
                          print("Сохранить изменения");
                        },
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
