# 🛠️ Contributing Guidelines

This document defines the standards I follow to keep consistency across commits, issues, and pull requests in this repository.

### ✅ Commits

Follow the [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) specification:

```
<type>(optional scope): <short description>

[optional body]

[optional footer]
```

#### Examples

```bash
feat(auth): add JWT token authentication
fix(api): handle null pointer on sale endpoint
docs(readme): update project structure section
```

#### Common types

| Type     | Description                                          |
|----------|------------------------------------------------------|
| feat     | New feature                                          |
| fix      | Bug fix                                              |
| docs     | Documentation changes                                |
| style    | Code style or formatting (no logic changes)          |
| refactor | Code improvement without changing behavior           |
| test     | Adding or updating tests                             |
| chore    | Maintenance tasks (builds, dependencies, configs)    |
| perf     | Performance improvement                              |
| ci       | CI/CD pipeline or workflow updates                   |

---

### Issues

- Use clear and concise titles.  
- Add labels when applicable (e.g., `bug`, `enhancement`, `question`).  
- The issue body should include:
  - Problem or suggestion description
  - Business rules or requirements (if applicable)
  - Steps to reproduce (for bugs)
  - Screenshots or logs, if relevant



### Pull Requests

- Keep PRs small and focused on a single purpose.  
- The title should follow the same format as commits (`feat:`, `fix:`, etc).  
- In the PR description, include:
  - Reference to related issues (e.g., `Closes #12`)
  - Explanation of what and why the changes were made
  - Testing instructions, if necessary  
- Mark as *draft* if still in progress (`[WIP]`).



<!-- ## 📌 Notes

- The project follows **Semantic Versioning (SemVer)**.  
- Commit messages can later be used to **auto-generate changelogs** or release notes. -->
