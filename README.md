Контроллер принимает String JSON тело, преобразует в объект, в ответ отдаётся String JSON

Валидация вынесена в .exception.utils.ValidationUtil

Применил @JsonView в RestController.createProduct чтобы он мог корректно принимать тело без поля productId
