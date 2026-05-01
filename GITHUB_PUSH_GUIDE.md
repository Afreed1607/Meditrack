gi# GitHub Push and Pull Request Instructions

## 📌 Prerequisites

Before pushing to GitHub, ensure you have:
- [GitHub account](https://github.com/signup) created
- [Git](https://git-scm.com/downloads) installed on your machine
- SSH or HTTPS configured for GitHub

## Step 1: Create a GitHub Repository

### Option A: Using GitHub Web Interface

1. Go to [github.com](https://github.com)
2. Click the **+** icon in top-right corner
3. Select **New repository**
4. Fill in the following:
   - Repository name: `MediTrack`
   - Description: `Clinic and Appointment Management System - Java Learning Project`
   - Choose **Public** (for academic project)
   - ✅ Add a .gitignore (select Java)
   - Add a README (we already have one, so skip)
   - Choose a license (MIT recommended)
5. Click **Create repository**
6. Copy the HTTPS URL (example: `https://github.com/yourusername/MediTrack.git`)

### Option B: Using GitHub CLI

If you have GitHub CLI installed:

```bash
gh repo create MediTrack --public --source=. --remote=origin --push
```

## Step 2: Add Remote Repository

Navigate to your MediTrack folder and add the remote:

```bash
cd C:\Users\USER\IdeaProjects\MediTrack

# Add remote (replace with your actual GitHub URL)
git remote add origin https://github.com/yourusername/MediTrack.git

# Verify remote
git remote -v
```

Expected output:
```
origin  https://github.com/yourusername/MediTrack.git (fetch)
origin  https://github.com/yourusername/MediTrack.git (push)
```

## Step 3: Set Default Branch

```bash
# If you want master as the default branch (current)
git branch -M master

# Or if you want main (GitHub's default)
git branch -M main
```

## Step 4: Push to GitHub

### First Push (Initial)

```bash
# Push master branch
git push -u origin master

# Or if renamed to main
git push -u origin main
```

You'll be prompted for credentials:
- If using HTTPS: Enter your GitHub username and **personal access token** (not password)
- If using SSH: No prompt needed (SSH key required)

### To Set Up SSH (Recommended)

```bash
# Generate SSH key (if you don't have one)
ssh-keygen -t ed25519 -C "your_email@example.com"

# Copy the public key to GitHub
# Settings → SSH and GPG keys → New SSH key
# Paste the contents of ~/.ssh/id_ed25519.pub
```

### Push Develop Branch

```bash
git push -u origin develop
```

## Step 5: Create a Pull Request (PR)

### Option A: Using GitHub Web Interface

1. Go to your repository on GitHub
2. You'll see a notification: "Compare & pull request"
3. Click the **Compare & pull request** button
4. Fill in PR details:

   **Title**: `feat: Complete MediTrack clinic management system`

   **Description:**
   ```markdown
   ## Overview
   Comprehensive clinic and appointment management system demonstrating Java fundamentals.

   ## Key Features
   - Doctor and patient management
   - Appointment scheduling and billing
   - Advanced search capabilities
   - Data persistence (CSV & serialization)

   ## Technical Highlights
   - 25 Java classes with OOP principles
   - Design patterns (Singleton, Factory, Strategy)
   - Generic collections and streams
   - Custom exceptions with chaining
   - Manual test suite (30/30 passing)

   ## Learning Objectives Met
   ✅ Core OOP implementation
   ✅ Design patterns
   ✅ Java 8+ features
   ✅ File I/O & persistence
   ✅ Exception handling
   ✅ Collections & generics

   ## Testing
   - Manual test suite: 30/30 ✅
   - Run: `mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.test.TestRunner"`

   ## Documentation
   - Setup_Instructions.md
   - JVM_Report.md
   - Design_Decisions.md
   - README.md

   Closes #1
   ```

5. Select base branch: **master** (or **main**)
6. Select compare branch: **develop**
7. Click **Create pull request**

### Option B: Using GitHub CLI

```bash
# Create and push PR
gh pr create --title "feat: Complete MediTrack clinic management system" \
             --body "Comprehensive Java clinic management system" \
             --base master \
             --head develop
```

### Option C: Using Git (then create PR on web)

```bash
# Push feature branch
git push -u origin develop

# Create PR URL for CLI
gh pr create --fill  # Interactive flow
```

## Step 6: Merge PR (Admin Access)

Once PR is reviewed and approved:

1. Click **Merge pull request** button on GitHub
2. Choose merge strategy:
   - **Create a merge commit**: Default, keeps history

**Merge commit message example:**

```
Merge pull request #1 from yourusername/develop

feat: Complete MediTrack clinic management system

- Implemented 25 Java classes
- Entity layer with proper inheritance
- Service layer with business logic
- Generic DataStore<T> for type safety
- Manual test suite (30/30 passing)
- Comprehensive documentation
```

3. Click **Confirm merge**
4. (Optional) Delete the branch after merge

## Step 7: Pull Latest Changes

```bash
# Switch to master
git checkout master

# Pull latest changes
git pull origin master
```

## Useful Git Commands for Collaboration

### Working with Branches

```bash
# List all branches
git branch -a

# Create new feature branch
git checkout -b feature/appointment-notifications

# Switch to branch
git checkout master

# Delete local branch
git branch -d feature/appointment-notifications

# Delete remote branch
git push origin --delete feature/appointment-notifications
```

### Committing Code

```bash
# View changes
git status

# Stage specific files
git add src/main/java/com/airtribe/meditrack/Main.java

# Stage all changes
git add .

# Commit
git commit -m "feat: Add email notifications for appointments"

# Push to remote
git push origin develop
```

### Viewing Commits

```bash
# View commit history
git log --oneline

# View detailed commit
git show <commit-hash>

# View changes in commit
git diff <commit-hash>^..<commit-hash>
```

## PR Review Checklist

Before merging a PR, verify:

- ✅ All tests pass (30/30)
- ✅ Code compiles without errors
- ✅ No merge conflicts
- ✅ Follows project conventions
- ✅ Documentation updated
- ✅ Meaningful commit messages

## Common Issues & Solutions

### Issue: "Permission denied (publickey)"
**Solution**: Set up SSH keys or use HTTPS with personal access token

### Issue: "fatal: 'origin' does not appear to be a git repository"
**Solution**: 
```bash
git remote add origin <your-github-url>
```

### Issue: "Merge conflict"
**Solution**: 
```bash
# Resolve conflicts in affected files
# Then:
git add .
git commit -m "Resolve merge conflicts"
git push origin develop
```

### Issue: "Your branch is behind origin"
**Solution**:
```bash
git pull origin develop
```

## GitHub Repository Structure

Once pushed, your GitHub repo should look like:

```
MediTrack/
├── master (main branch)
│   └── All commits
├── develop (development branch)
│   └── Where PRs merge into
├── .gitignore
├── .github/workflows/ (for CI/CD - optional)
├── README.md
├── docs/
├── src/
├── pom.xml
└── LICENSE
```

## Setting Up GitHub Actions (Optional)

Create `.github/workflows/java.yml` for CI/CD:

```yaml
name: Java CI

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v2
    
    - name: Set up JDK 21
      uses: actions/setup-java@v2
      with:
        java-version: '21'
    
    - name: Build with Maven
      run: mvn clean compile
    
    - name: Run tests
      run: mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.test.TestRunner"
```

## Example GitHub URL Formats

After pushing, your repository will be accessible at:

```
https://github.com/yourusername/MediTrack
```

PR URL (after creating):
```
https://github.com/yourusername/MediTrack/pull/1
```

## Final Verification

```bash
# View remote
git remote -v

# View all branches (local and remote)
git branch -a

# View recent commits
git log --oneline -10

# Check git status
git status
```

## Summary of Commands (Quick Reference)

```bash
# Initial setup
git config user.email "your.email@example.com"
git config user.name "Your Name"

# Add remote
git remote add origin https://github.com/yourusername/MediTrack.git

# First push
git push -u origin master

# Create and push develop
git checkout -b develop
git push -u origin develop

# Future pushes
git push origin master
git push origin develop

# Create PR (on GitHub web interface or use CLI)
gh pr create --base master --head develop
```

---

**Status**: Ready for GitHub  
**Repository**: Public  
**License**: MIT  
**Collaborators**: Add via GitHub settings  

Your MediTrack project is ready to share with the world! 🚀


