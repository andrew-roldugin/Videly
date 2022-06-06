import 'package:client/app_view_model.dart';
import 'package:client/domain/models/edit_channel_request.dart';
import 'package:client/domain/models/edit_user_request.dart';
import 'package:client/domain/models/entity/channel_model.dart';
import 'package:client/domain/models/entity/user_model.dart';
import 'package:client/http/api_exception.dart';
import 'package:client/pages/error_page.dart';
import 'package:client/repositories/user_repository.dart';
import 'package:client/screens/create_channel.dart';
import 'package:client/screens/splash_screen.dart';
import 'package:client/services/auth_service.dart';
import 'package:client/services/channel_service.dart';
import 'package:client/services/user_service.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:provider/provider.dart';
import 'package:toggle_switch/toggle_switch.dart';

import '../widgets/common/modal_bottom_sheet.dart';

class _AccountPageViewModel extends ChangeNotifier {
  final _channelService = GetIt.I<ChannelService>();
  final _userService = GetIt.I<UserService>();

  late EditChannelRequest? _editChannelRequest = null;
  late final EditUserRequest? _editUserRequest = EditUserRequest(password: "");
  late final UserRepository _userRepository;
  String err = "";

  UserModel? get user => _userRepository.user;

  String get userLogin => user?.email ?? "Загрузка...";
  bool isLoading = true, onProcess = false;

  ChannelModel? get channel => user?.channel;

  _AccountPageViewModel(BuildContext ctx) {
    _userRepository =
        Provider.of<AppViewModel>(ctx, listen: false).userRepository;
    asyncInit();
  }

  void asyncInit() async {
    isLoading = true;
    notifyListeners();

    await _userService.getUserData().then((value) {
      _userRepository.user = value;
      if (user != null && user?.channel != null) {
        _editChannelRequest =
            EditChannelRequest.fromChannelModel(user!.channel!);
      }
    }).onError((error, stackTrace) {
      if (error is DioError) {
        err = error.response?.data['messages'][0];
      } else {
        err = error.toString();
      }
      // throw ApiException(messages: res.data['messages']);
    });

    isLoading = false;
    notifyListeners();
  }

  String? get channelName => _editChannelRequest?.name;

  set channelName(v) => _editChannelRequest?.name = v;

  void onSaveButtonClick(BuildContext context) async {
    onProcess = true;
    notifyListeners();

    if (channel != null && _editChannelRequest != null) {
      await _channelService
          .editChannel(channel!.id, _editChannelRequest!)
          .then((value) {
        MyBottomSheet.show(context, value!);
        // _userRepository.user.channel = value;
      }).onError(
        (error, stackTrace) {
          if (error is DioError) {
            err = error.response?.data['messages'][0];
          } else {
            err = error.toString();
          }
        },
      );
    }

    if (_editUserRequest != null &&
        _editUserRequest?.password.isNotEmpty == true) {
      await _userService.edit(user!.id, _editUserRequest!)
      .then((value){
        MyBottomSheet.show(context, value!);
      })
          .onError(
        (error, stackTrace) {
          if (error is DioError) {
            err = error.response?.data['messages'][0];
          } else {
            err = error.toString();
          }
        },
      );
    }
    onProcess = false;
    notifyListeners();
  }

  void onLogoutButtonClick(context) {
    GetIt.I<AuthService>().signOut().then((value) {
      Navigator.pushNamed(context, SplashScreen.routeName);
    });
  }

  void onRemoveButtonClick(BuildContext context) {
    onLogoutButtonClick(context);
    if (user != null) {
      _userService.deleteAccount(user!.id).then((value) {
        onLogoutButtonClick(context);
      });
    }
  }
}

class AccountPage extends StatelessWidget {
  const AccountPage({Key? key}) : super(key: key);

  static Widget create() {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (ctx) => _AccountPageViewModel(ctx)),
        Provider(create: (_) => AppViewModel())
      ],
      child: const AccountPage(),
    );
  }

  @override
  Widget build(BuildContext context) {
    final model = Provider.of<_AccountPageViewModel>(context);

    // if ( model.channel == null) {
    //   Navigator.of(context).pushNamed(CreateChannelScreen.routeName);
    //   return Container();
    // }

    if (model.err.isNotEmpty) {
      return ErrorPage(message: model.err);
    }

    if (model.isLoading) {
      return const Center(
        child: CircularProgressIndicator(),
      );
    }

    return SafeArea(
      child: Scrollbar(
        child: SingleChildScrollView(
          child: Padding(
            padding: const EdgeInsets.symmetric(vertical: 15, horizontal: 15),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              mainAxisAlignment: MainAxisAlignment.start,
              children: [
                const _UserLoginWidget(),
                const SizedBox(height: 15),
                const _EditPasswordWidget(),
                const SizedBox(height: 15),
                model.channel != null
                    ? Column(children: [
                        const _EditDescriptionWidget(),
                        const SizedBox(height: 15),
                        const _EditChannelNameWidget(),
                        const SizedBox(height: 15),
                        _ToggleButton(
                            label: 'Возможность оценивания',
                            variants: const ['Нет', 'Да'],
                            initial: model.channel?.allowRating == true ? 1 : 0,
                            callback: (x) {
                              model._editChannelRequest?.allowRating = x;
                            }),
                        const SizedBox(height: 15),
                        _ToggleButton(
                            initial:
                                model.channel?.allowComments == true ? 1 : 0,
                            label: 'Возможность комментирования',
                            variants: const ['Нет', 'Да'],
                            callback: (x) {
                              model._editChannelRequest?.allowComments = x;
                            }),
                        const Padding(
                          padding: EdgeInsets.symmetric(
                              horizontal: 15, vertical: 20),
                          // child: _UploadImageArea(),
                        ),
                      ])
                    : Container(),
                const _ButtonBar(),
                Center(
                  child: MaterialButton(
                    height: 35,
                    color: const Color.fromRGBO(65, 75, 178, 1),
                    child: const Text(
                      "Сохранить изменения",
                      style: TextStyle(color: Colors.white),
                    ),
                    disabledColor: const Color.fromRGBO(65, 75, 178, .1),
                    onPressed: !model.onProcess
                        ? () => model.onSaveButtonClick(context)
                        : null,
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

class _ButtonBar extends StatelessWidget {
  const _ButtonBar({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    var model = context.read<_AccountPageViewModel>();
    return Row(
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
            model.onRemoveButtonClick(context);
          },
        ),
        MaterialButton(
          onPressed: () {
            model.onLogoutButtonClick(context);
          },
          color: const Color.fromRGBO(65, 75, 178, 1),
          child: Text(
            "Выйти из аккаунта",
            style: Theme.of(context).textTheme.bodyText1,
          ),
        )
      ],
    );
  }
}

class _UploadImageArea extends StatelessWidget {
  const _UploadImageArea({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialButton(
      height: 110,
      shape: const RoundedRectangleBorder(
          side: BorderSide(width: 1.5),
          borderRadius: BorderRadius.all(Radius.circular(5.0))),
      onPressed: () {
        print('+');
      },
      child: Center(
        child: Column(
          //alignment: Alignment.topCenter,
          children: const [
            Text(
              '+',
              style: TextStyle(fontWeight: FontWeight.bold, fontSize: 50),
              maxLines: 1,
              overflow: TextOverflow.ellipsis,
            ),
            Text(
              'Добавить фото фона',
              style: TextStyle(fontWeight: FontWeight.bold, fontSize: 24),
              maxLines: 1,
              overflow: TextOverflow.ellipsis,
            ),
          ],
        ),
      ),
    );
  }
}

typedef callbackFN = void Function(bool on);

class _ToggleButton extends StatelessWidget {
  final String label;
  final List<String> variants;
  final callbackFN callback;
  final int initial;

  const _ToggleButton({
    Key? key,
    required this.initial,
    required this.label,
    required this.variants,
    required this.callback,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
      Text(
        label,
        style: const TextStyle(fontSize: 16),
        maxLines: 1,
        overflow: TextOverflow.ellipsis,
      ),
      ToggleSwitch(
        minWidth: 50.0,
        initialLabelIndex: initial,
        cornerRadius: 20.0,
        activeFgColor: Colors.white,
        inactiveBgColor: Colors.grey,
        inactiveFgColor: Colors.white,
        totalSwitches: 2,
        labels: variants,
        activeBgColors: const [
          [Colors.pink],
          [Colors.blue]
        ],
        onToggle: (index) {
          callback(index == 1);
        },
      ),
    ]);
  }
}

class _EditPasswordWidget extends StatelessWidget {
  const _EditPasswordWidget({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final model = Provider.of<_AccountPageViewModel>(context, listen: false);

    return TextFormField(
      autovalidateMode: AutovalidateMode.onUserInteraction,
      validator: (v) => v == null || v.length < 6
          ? "Длина пароля должна составлять не менее 6 символов"
          : null,
      textInputAction: TextInputAction.next,
      keyboardType: TextInputType.visiblePassword,
      obscureText: true,
      decoration: InputDecoration(
        fillColor: Colors.white,
        filled: true,
        prefixIcon: const Icon(Icons.account_circle),
        hintText: "Пароль",
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(10),
        ),
      ),
      onChanged: (v) {
        model._editUserRequest?.password = v;
      },
    );
  }
}

class _EditDescriptionWidget extends StatelessWidget {
  const _EditDescriptionWidget({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final model = Provider.of<_AccountPageViewModel>(context, listen: false);
    return TextFormField(
      maxLines: 5,
      initialValue: model.channel?.about,
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
      onChanged: (v) {
        model._editChannelRequest?.about = v;
      },
    );
  }
}

class _EditChannelNameWidget extends StatelessWidget {
  const _EditChannelNameWidget({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final model = Provider.of<_AccountPageViewModel>(context);
    final channelName = model.channelName;

    return channelName != null
        ? TextFormField(
            initialValue: channelName,
            maxLines: 1,
            validator: (v) => v != null && v.trim().isNotEmpty
                ? null
                : "Поле обязательно для заполнения",
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
            onChanged: (v) {
              model.channelName = v;
            },
          )
        : Container();
  }
}

class _UserLoginWidget extends StatelessWidget {
  const _UserLoginWidget({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final login = context.read<_AccountPageViewModel>().userLogin;
    return Text(
      'Ваш логин: $login',
      style: const TextStyle(
          fontSize: 24,
          fontWeight: FontWeight
              .bold), // TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
      maxLines: 1,
      overflow: TextOverflow.ellipsis,
    );
  }
}
