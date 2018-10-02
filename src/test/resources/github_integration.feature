Feature: check service for github integration
  Scenario: fetch info about repository
    Given a author krzpob
    And he has a repo jug-factor
    When call service
    Then get fullName of repo
    And amount of stars
    And url for clonning repo
    And empty description