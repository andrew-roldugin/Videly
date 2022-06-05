import 'package:client/services/auth_service.dart';
import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:provider/provider.dart';
import 'package:validators/validators.dart' as validator;

class _ResetPasswordFormViewForm{
  final _emailInputController = TextEditingController();
  final _formKey = GlobalKey<FormState>();
  late final AuthService _authService;

  _ResetPasswordFormViewForm() {
    _authService = GetIt.I<AuthService>();
  }

  set password(String newPassword) {
    _emailInputController.text = newPassword;
  }

  void _resetPassword() {
    final isValid = _formKey.currentState?.validate() ?? false;
    if (!isValid) {
      return;
    }
    _authService.resetPassword(email: _emailInputController.text);
  }
}

class ResetPasswordForm extends StatelessWidget {
  const ResetPasswordForm({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Provider(create: (_) => _ResetPasswordFormViewForm(), child: _ResetPasswordFormWidget(),);
  }
}

class _ResetPasswordFormWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    var model = context.read<_ResetPasswordFormViewForm>();
    return Padding(
      padding: const EdgeInsets.all(10.0),
      child: Column(
        children: [
          Form(
            key: model._formKey,
            child: TextFormField(
              validator: (v) => v != null && validator.isEmail(v)
                  ? null
                  : "Введите корректный Email",
              textInputAction: TextInputAction.next,
              keyboardType: TextInputType.emailAddress,
              decoration: InputDecoration(
                fillColor: Colors.white,
                filled: true,
                prefixIcon: const Icon(Icons.account_circle),
                hintText: "Email",
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10),
                ),
              ),
              onChanged: (v) => model.password = v,
            ),
          ),
          Center(
            child: MaterialButton(
              height: 35,
              color: const Color.fromRGBO(65, 75, 178, 1),
              child: Text(
                "Отправить письмо",
                style: Theme.of(context).textTheme.bodyText1,
              ),
              onPressed: () => model._resetPassword(),
            ),
          )
        ],
      ),
    );
  }
}