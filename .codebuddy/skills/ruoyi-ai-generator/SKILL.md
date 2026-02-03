---
name: ruoyi-ai-generator
description: This skill should be used when the user needs AI-powered code generation for RuoYi-Vue-Plus framework. It handles intelligent generation of Java backend code (Controller, Service, Mapper, Domain) and Vue3 frontend code (Vue pages, TypeScript API, Types) based on database table structures, replacing or enhancing the traditional Velocity template approach.
---

# RuoYi AI 代码生成器

## Overview

This skill enables AI-driven code generation for RuoYi-Vue-Plus framework, providing intelligent code generation that understands business context from table names, comments, and column definitions. It can generate production-ready Java backend code and Vue3 frontend code with minimal manual adjustments.

**Key Capabilities:**
- Parse database table structures (GenTable/GenTableColumn)
- Generate intelligent Java backend code (Controller, Service, Mapper, Domain, VO, BO)
- Generate Vue3 frontend code (Pages, TypeScript API, Type definitions)
- Generate SQL menu scripts
- Understand business semantics from table comments

## When to Use

Use this skill when:
1. User requests AI-powered code generation for RuoYi projects
2. User wants to generate code from database tables with intelligent business logic
3. User needs to customize or replace Velocity templates
4. User asks about "AI code generation", "smart code generator", or "intelligent CRUD generation"
5. Working with `ruoyi-generator` module files

## Workflow Decision Tree

```
User Request
    │
    ├──► "生成XX表的代码" / "Generate code for table"
    │       └──► Analyze table structure → Generate complete code → Output files
    │
    ├──► "优化模板" / "Improve templates"
    │       └──► Load references/ruoyi-architecture.md → Analyze → Suggest improvements
    │
    ├──► "添加AI生成功能" / "Add AI generation"
    │       └──► Read GenTableServiceImpl → Create AiCodeGenerator → Integrate
    │
    └──► "对比AI和Velocity" / "Compare AI vs Velocity"
            └──► Load references/comparison.md → Analyze trade-offs → Recommend
```

## Core Capabilities

### 1. Intelligent Code Analysis

Before generating code, analyze:
- Table name patterns (e.g., `sys_user` = system user management)
- Column comments for business semantics
- Field types and constraints
- Relationships between tables

**Load reference:** `references/ruoyi-architecture.md` for framework conventions

### 2. Java Backend Code Generation

Generate these components with intelligent defaults:

| Component | Location Pattern | AI Enhancement |
|-----------|-----------------|----------------|
| Domain | `domain/{ClassName}.java` | Smart field validation, comments |
| VO | `domain/vo/{ClassName}Vo.java` | View-oriented field selection |
| BO | `domain/bo/{ClassName}Bo.java` | Business operation fields |
| Mapper | `mapper/{ClassName}Mapper.java` | Custom SQL for complex queries |
| Service | `service/I{ClassName}Service.java` | Business method signatures |
| ServiceImpl | `service/impl/{ClassName}ServiceImpl.java` | Implementation with validation |
| Controller | `controller/{ClassName}Controller.java` | REST API with permissions |

### 3. Vue3 Frontend Code Generation

Generate modern Vue3 + TypeScript code:

| Component | Location Pattern | Features |
|-----------|-----------------|----------|
| API | `api/{module}/{business}/index.ts` | Axios calls, error handling |
| Types | `api/{module}/{business}/types.ts` | TypeScript interfaces |
| Vue Page | `views/{module}/{business}/index.vue` | CRUD table, forms, dialogs |

### 4. Code Quality Guidelines

Generated code should follow RuoYi conventions:
- Use Lombok annotations (`@Data`, `@RequiredArgsConstructor`)
- Include Bean Validation (`@NotBlank`, `@Size`)
- Add Swagger annotations (`@Schema`)
- Follow RESTful API design
- Include proper exception handling
- Generate meaningful comments from table metadata

## Generation Strategies

### Strategy 1: Full AI Generation (High Flexibility)
```
Table Structure → AI Analysis → Direct Code Output
```
Best for: Complex business logic, custom requirements

### Strategy 2: Template Enhancement (Balanced)
```
Table Structure → AI Enhance → Velocity Template → Code Output
```
Best for: Consistent structure with intelligent content

### Strategy 3: Hybrid (Recommended)
```
Simple CRUD → Velocity Template
Complex Logic → AI Generation
```
Best for: Production use, optimal speed and quality

## Implementation Guide

### Adding AI Generation to RuoYi

1. **Create AiCodeGenerator Service**
   ```
   Path: ruoyi-modules/ruoyi-generator/src/main/java/org/dromara/generator/service/AiCodeGenerator.java
   ```

2. **Modify GenTableServiceImpl**
   - Add AI generation branch in `previewCode()` method
   - Add configuration flag for AI mode

3. **Create AI Prompt Builder**
   - Convert GenTable/GenTableColumn to structured prompt
   - Include framework conventions

**Reference:** `references/gen-table-schema.md` for data structure details

### Prompt Engineering Template

```java
String buildGenerationPrompt(GenTable table, String codeType) {
    return """
        Generate %s code for RuoYi-Vue-Plus framework.
        
        Table Information:
        - Name: %s
        - Comment: %s
        - Class Name: %s
        - Module: %s
        - Business: %s
        
        Columns:
        %s
        
        Requirements:
        1. Follow RuoYi naming conventions
        2. Use proper validation annotations
        3. Include Swagger documentation
        4. Handle relationships if any
        5. Return only the code, no explanations
        """.formatted(
            codeType,
            table.getTableName(),
            table.getTableComment(),
            table.getClassName(),
            table.getModuleName(),
            table.getBusinessName(),
            formatColumns(table.getColumns())
        );
}
```

## Resources

### references/
- `ruoyi-architecture.md` - Framework architecture and conventions
- `gen-table-schema.md` - GenTable/GenTableColumn data structures
- `velocity-templates.md` - Existing Velocity template reference
- `code-examples.md` - High-quality code examples for AI training

### assets/
- `java-templates/` - Java code templates
- `vue-templates/` - Vue3 code templates

## Best Practices

1. **Always validate AI-generated code** before using in production
2. **Cache AI responses** for identical table structures to save costs
3. **Use hybrid approach** - Velocity for structure, AI for content
4. **Maintain fallback** - Always have Velocity as backup
5. **Version control** generated code for auditing

## Integration Points

Key files to integrate with:
- `ruoyi-modules/ruoyi-generator/src/main/java/org/dromara/generator/service/GenTableServiceImpl.java`
- `ruoyi-modules/ruoyi-generator/src/main/java/org/dromara/generator/util/VelocityUtils.java`
- `ruoyi-modules/ruoyi-generator/src/main/java/org/dromara/generator/domain/GenTable.java`
- `ruoyi-modules/ruoyi-generator/src/main/java/org/dromara/generator/domain/GenTableColumn.java`
