# Steps needed to prepare a new release

# First PR for new release

- Create a new release branch
- Checkout the release tags for `QuantLib` and `QuantLib-SWIG` for the git submodules in the
  folder `external`
- Update the version in `README.md`
- Update the environment variable `QUANTLIB_VERSION` from `SNAPSHOT` to the new release tag
  for the `.github/workflows/build_maven_artefact.yml` workflow
- Merge Pull Request

# Update the repository

- Prepare a new release
- Close the associated milestone
- Add a new milestone for the next release

# Second PR to dump to next `SNAPSHOT` version

- Update the environment variable `QUANTLIB_VERSION` from the new release tag to the next `SNAPSHOT`
  release in `.github/workflows/build_maven_artefact.yml`
- Merge Pull Request

