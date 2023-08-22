# ItalianDocsValidationJava

The **ItalianDocsValidation** library is a versatile tool designed to handle the validation and generation of Italian tax codes and related documents. It offers a range of functions that simplify the management of Italian document data, making it an invaluable asset for developers working on projects involving Italian documents.

## Twin Libraries: ItalianDocsValidationJs

The **ItalianDocsValidationJava** library is part of a twin set of libraries designed to provide seamless validation and handling of Italian tax codes and related documents. It has a sibling library written in Javascript that offers similar functionalities for Javascript-based projects.

The Java counterpart, known as **ItalianDocsValidationJava**, offers developers working in the Javascript environment a powerful tool to manage Italian document data, just like its JavaScript counterpart does for JavaScript-based projects. Both libraries share a common goal of simplifying the validation, generation, and extraction of Italian document information, making them valuable resources for developers dealing with Italian documents in different programming languages.

For more information about the Javascript twin library, please visit the [ItalianDocsValidationJs repository](https://github.com/Underscore2/ItalianDocsValidationJs).


### (Alpha) Version 0.1.0

## Installation

Install the library using maven:

```bash
TODO
```

## Usage
Here's how you can use the library to generate a Codice Fiscale starting from an object of type Person:

```java
public static void main(String[] args) {
        Person person = Person
        .builder()
        .firstName("Mario")
        .lastName("Rossi")
        .birthplace("Milano")
        .birthplaceInitials("MI")
        .gender("M")
        .dateOfBirth("25/05/1984")
        .build();
        String codiceFiscale = CodiceFiscale.generateCodiceFiscale(person);
        System.out.println("Codice fiscale: " + codiceFiscale);

        String reverseCodiceFiscale = CodiceFiscale.reverseCodiceFiscale(codiceFiscale);
        System.out.println("Reverse codice fiscale: " + reverseCodiceFiscale);
}

```

For more detailed information about each function and their usage, refer to the library's documentation.

## Contributions

This library is open source and welcomes contributions from the community. If you're interested in improving the library, adding new features, or fixing issues, you're invited to participate. Both beginners and experts are welcome!

### How to Contribute

1. Fork the [repository](https://github.com/Underscore2/ItalianDocsValidationJs) and clone your fork to your local machine.
2. Create a new branch for your work: `git checkout -b feature/your-feature`,`git checkout -b ref/your-refactoring`.
3. Make your changes and additions to the code.
4. Ensure the code is well tested and all tests pass.
5. Commit your changes: `git commit -m "feat(FILE || CROSS): description"`, `git commit -m "ref(FILE || CROSS): description"` .
6. Push your branch to your fork: `git push origin feat/new-feature`.
7. Submit a pull request to the main repository.
8. Await feedback and collaborate with the community to review and improve your work.

### Contribution Guidelines

- Make sure your code follows the project's coding standards.
- Add appropriate tests for any new features or changes you introduce (JEST).
- Keep the documentation up to date with any significant changes.
- Be respectful and collaborative with other contributors.

Feel free to join the discussion in pull request comments or the issues area if you have questions or suggestions. Thank you for your contribution!

## License
This library is released under the MIT License. See LICENSE for details.
