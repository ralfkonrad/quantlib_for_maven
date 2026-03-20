# Steps needed to prepare a new release

## First PR for new release

- Create a new release branch from `master`
- Checkout the release tags for `QuantLib` and `QuantLib-SWIG` for the git submodules in the
  folder `external`
- Update the version in `README.md`
- Update the environment variable `QUANTLIB_VERSION` from `SNAPSHOT` to the new release tag
  for the `.github/workflows/build_maven_artefact.yml` workflow
- Update the `cmake` project version in `CMakeLists.txt`
- Note: the `java/pom.xml` version (`0.1.0-SNAPSHOT`) is **not** changed manually;
  the CI workflow sets the actual version via `mvn versions:set` using `QUANTLIB_VERSION`
- Verify that the CI build passes on the release branch
- Merge Pull Request

## Update the repository

- Prepare a new release (the deploy to Maven Central happens automatically
  when the PR is merged to `master`,
  see the `deploy-quantlib-snapshot` job in `build_maven_artefact.yml`)
- Close the associated milestone
- Add a new milestone for the next release

## Second PR to bump to next `SNAPSHOT` version

- Update the environment variable `QUANTLIB_VERSION` from the new release tag to the next `SNAPSHOT`
  release in `.github/workflows/build_maven_artefact.yml`
- Merge Pull Request

